package com.example.activity_service.model.dto.response;

import com.example.activity_service.model.dto.NewsFeed;
import lombok.Data;

import java.util.List;

@Data
public class NewsFeedResponse {
    private List<NewsFeed> newsFeeds;
}
