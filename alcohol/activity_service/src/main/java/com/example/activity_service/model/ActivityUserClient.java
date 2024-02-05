package com.example.activity_service.model;

import com.example.activity_service.model.dto.PostUserDto;
import com.example.common.response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "UserToActivityFeignClient", url = "http://localhost:8081/api/v1/internal/user")
public interface ActivityUserClient {
    @RequestMapping(method = RequestMethod.GET,value = "/{userId}")
    Response<PostUserDto> getUser(@PathVariable("userId") Long userId);
}
