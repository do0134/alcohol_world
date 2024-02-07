package com.example.newsfeed_service.service;

import com.example.common.response.Response;
import com.example.newsfeed_service.model.NewsfeedActivityFeignClient;
import com.example.newsfeed_service.model.dto.Newsfeed;
import com.example.newsfeed_service.model.dto.response.NewsfeedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsFeedService {
    private final NewsfeedActivityFeignClient newsfeedActivityFeignClient;

    public NewsfeedResponse getNewsFeed(Long userId) {
        Response<List<Newsfeed>> newsfeedResponse = newsfeedActivityFeignClient.getNewsfeed(userId);

        if (!newsfeedResponse.getResultCode().equals("SUCCESS")) {
            return new NewsfeedResponse();
        }

        NewsfeedResponse newsFeed = new NewsfeedResponse();
        newsFeed.setNewsfeedList(newsfeedResponse.getResult());

        return newsFeed;
    }
}
