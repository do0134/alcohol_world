package com.example.order_service.service.impl;

import com.example.common.error.AlcoholException;
import com.example.common.error.ErrorCode;
import com.example.common.response.Response;
import com.example.order_service.model.dto.Order;
import com.example.order_service.model.dto.OrderItem;
import com.example.order_service.model.dto.OrderUser;
import com.example.order_service.model.entity.OrderEntity;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.service.ItemFeignClient;
import com.example.order_service.service.OrderService;
import com.example.order_service.service.UserFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ItemFeignClient itemFeignClient;
    private final UserFeignClient userFeignClient;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Order makeOrder(Long userId, Long itemId, Long quantity) {
        OrderUser orderUser = getOrderUser(userId);
        OrderItem orderItem = getOrderItem(itemId);
        checkTime(orderItem.getStart_time(), orderItem.getEnd_time());
        Long totalPrice = (long)quantity*orderItem.getPrice();
        Order order = Order.toDto(orderUser,orderItem,quantity,totalPrice);
        redisTemplate.opsForHash().put("Order",getOrderRedisKey(userId,itemId),order);
        return order;
    }

    @Override
    public List<Order> getUserOrder(Long userId) {
        Optional<List<OrderEntity>> orderEntityList = orderRepository.findAllByUserId(userId);
        List<Order> orderList = new ArrayList<>();

        if (orderEntityList.isEmpty()) {
            return orderList;
        }

        for (OrderEntity orderEntity:orderEntityList.get()) {
            OrderUser orderUser = getOrderUser(orderEntity.getUserId());
            OrderItem orderItem = getOrderItem(orderEntity.getItemId());
            orderList.add(Order.toDto(orderUser, orderItem, orderEntity.getQuantity(), orderEntity.getTotalPrice()));
        }

        return orderList;
    }

    @Override
    public Order getOrder(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new AlcoholException(ErrorCode.NO_SUCH_ORDER));
        OrderUser orderUser = getOrderUser(orderEntity.getUserId());
        OrderItem orderItem = getOrderItem(orderEntity.getItemId());
        return Order.toDto(orderUser, orderItem, orderEntity.getQuantity(), orderEntity.getTotalPrice());
    }

    @Override
    @Transactional
    public Order pay(Long userId, Long itemId) {
        try {
            Order order = (Order) redisTemplate.opsForHash().get("Order", getOrderRedisKey(userId, itemId));
            System.out.println(order);
            Object stockObject = redisTemplate.opsForHash().get("SalesItem", getItemRedisKey(itemId));
            System.out.println(stockObject);
            Long stock = (Long) stockObject;
            updateStock(itemId, stock-1);
            saveOrder(order, userId, itemId);
            return order;
        } catch (Exception e) {
            throw new AlcoholException(ErrorCode.NO_SUCH_ORDER);
        }
    }

    public void saveOrder(Order order, Long userId, Long itemId) {
        OrderEntity.toEntity(userId, itemId, order.getQuantity(), order.getTotalPrice());
    }

    public Boolean checkTime(Timestamp startTime, Timestamp endTime) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        return now.after(startTime) && now.before(endTime);
    }

    public OrderUser getOrderUser(Long userId) {
        Response<OrderUser> orderUser = userFeignClient.getUser(userId);
        if (!orderUser.getResultCode().equals("SUCCESS")) {
            throw new AlcoholException(ErrorCode.USER_NOT_FOUND);
        }

        return orderUser.getResult();
    }

    public OrderItem getOrderItem(Long itemId) {
        Response<OrderItem> orderItem = itemFeignClient.getOrderItem(itemId);

        if (!orderItem.getResultCode().equals("SUCCESS")) {
            throw new AlcoholException(ErrorCode.NO_SUCH_ITEM);
        }

        return orderItem.getResult();
    }

    public String getOrderRedisKey(Long userId, Long itemId) {
        return String.format("%s order %s",userId, itemId);
    }

    public String getItemRedisKey(Long itemId) {
        return "SalesItem:" + String.valueOf(itemId);
    }

    public void updateStock(Long itemId, Long stock) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        hashOperations.putAll("SalesItem", getRedisHash(itemId, stock));
        log.info(String.format("Stream for item %s has changed. Update inventory is %s.", String.valueOf(itemId), String.valueOf(stock)));
    }

    public Map<String, Object> getRedisHash(Long itemId, Long stock) {
        String key = getItemRedisKey(itemId);
        Map<String, Object> map = new HashMap<>();
        map.put(key, String.valueOf(stock));
        return map;
    }
}
