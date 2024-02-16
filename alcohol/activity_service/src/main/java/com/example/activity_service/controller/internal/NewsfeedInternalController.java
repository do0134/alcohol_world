package com.example.activity_service.controller.internal;

import com.example.activity_service.model.dto.NewsFeed;
import com.example.activity_service.model.dto.response.NewsResponse;
import com.example.activity_service.service.NewsFeedService;
import com.example.common.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/internal/posts")
public class NewsfeedInternalController {

    private final NewsFeedService newsFeedService;

    @GetMapping("/newsfeed/{userId}")
    public Response<List<NewsFeed>> getNewsfeed(@PathVariable("userId") Long userId) {
        List<NewsFeed> newsFeedResponse = newsFeedService.newsFeed(userId);
        return Response.success(newsFeedResponse);
    }

    @GetMapping("/news/{userId}")
    public Response<NewsResponse> getNews(@PathVariable("userId") Long userId) {
        return Response.success(new NewsResponse());
    }
}
