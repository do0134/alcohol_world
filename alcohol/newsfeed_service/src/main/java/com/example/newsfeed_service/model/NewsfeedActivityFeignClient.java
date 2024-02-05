package com.example.newsfeed_service.model;

import com.example.common.response.Response;
import com.example.newsfeed_service.model.dto.News;
import com.example.newsfeed_service.model.dto.Newsfeed;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@FeignClient(name = "ActivityToNewsfeedFeignClient", url = "http://localhost:8082/api/v1/internal")
public interface NewsfeedActivityFeignClient {
    @RequestMapping(method = RequestMethod.GET,value = "/newsfeed/{userId}")
    Response<List<Newsfeed>> getNewsfeed(@PathVariable("userId") Long userId);

    @RequestMapping(method = RequestMethod.GET, value = "/news/{userId}")
    Response<List<News>> getNews(@PathVariable("userId") Long userId);
}
