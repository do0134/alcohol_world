package com.example.activity_service.controller.internal;

import com.example.activity_service.model.dto.response.NewsFeedResponse;
import com.example.activity_service.model.dto.response.NewsResponse;
import com.example.common.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/internal")
public class NewsfeedInternalController {

    @GetMapping("/newsfeed/{userId}")
    public Response<NewsFeedResponse> getNewsfeed(@PathVariable("userId") Long userId) {
        return Response.success(new NewsFeedResponse());
    }

    @GetMapping("/news/{userId}")
    public Response<NewsResponse> getNews(@PathVariable("userId") Long userId) {
        return Response.success(new NewsResponse());
    }
}
