package com.example.newsfeed_service.model;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ActivityToNewsfeedFeignClient", url = "http://localhost:8082/api/v1/internal/user")
public interface NewsfeedActivityFeignClient {

}
