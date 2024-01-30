package com.example.alchohol.post.controller.Response;

import com.example.alchohol.post.models.dto.Activate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class NewsFeedResponse {
    private List<Activate> feeds;
}
