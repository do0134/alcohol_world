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

    @Override
    @Transactional
    public Order makeOrder(Long userId, Long itemId, Long quantity) {
        OrderUser orderUser = getOrderUser(userId);
        OrderItem orderItem = getOrderItem(itemId);
        Long totalPrice = (long)quantity*orderItem.getPrice();
        orderRepository.save(OrderEntity.toEntity(userId, itemId,quantity, totalPrice));
        return Order.toDto(orderUser,orderItem,quantity,totalPrice);
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
}
