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
import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            Long quantity = order.getQuantity();
            redisTemplate.opsForStream().range("SalesItem", Range.closed("+", "-"));

        } catch (Exception e) {
            throw new AlcoholException(ErrorCode.NO_SUCH_ORDER);
        }

        return new Order();
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
        String redisKey = "SalesItem";
        return redisKey + ": " + itemId;
    }
}
