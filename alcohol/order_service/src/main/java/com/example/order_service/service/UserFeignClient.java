package com.example.order_service.service;

import com.example.common.response.Response;
import com.example.order_service.model.dto.OrderUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "UserToOrderFeignClient", url = "http://localhost:8081/api/v1/internal/user")
public interface UserFeignClient {
    @RequestMapping(method = RequestMethod.GET,value = "/order/{userId}")
    Response<OrderUser> getUser(@PathVariable("userId") Long userId);
}
