package com.example.order_service.service;

import com.example.common.response.Response;
import com.example.order_service.model.dto.OrderItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "ItemToOrderFeignClient", url = "http://localhost:8084/api/v1/internal")
public interface ItemFeignClient {
    @RequestMapping(method = RequestMethod.GET,value = "/order/{itemId}")
    Response<OrderItem> getOrderItem(@PathVariable("itemId") Long itemId);
}
