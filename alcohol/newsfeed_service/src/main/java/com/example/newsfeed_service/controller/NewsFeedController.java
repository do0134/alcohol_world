package com.example.newsfeed_service.controller;

import com.example.common.response.Response;
import com.example.newsfeed_service.model.dto.response.NewsfeedResponse;
import com.example.newsfeed_service.service.NewsFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/newsfeed")
public class NewsFeedController {
    private final NewsFeedService newsFeedService;

    @GetMapping("/{userId}")
    public Response<NewsfeedResponse> getNewsFeed(@PathVariable("userId") Long userId) {
        return Response.success(newsFeedService.getNewsFeed(userId));
    }
}
