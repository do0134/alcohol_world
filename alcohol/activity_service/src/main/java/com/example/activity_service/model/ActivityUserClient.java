package com.example.activity_service.model;

import com.example.activity_service.model.dto.PostUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "UserToActivityFeignClient", url = "http://localhost:8081/api/v1/user")
public interface ActivityUserClient {
    @RequestMapping(method = RequestMethod.GET,value = "/{userId}")
    PostUserDto getUser(@PathVariable("userId") Long userId);
}
