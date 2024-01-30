package com.example.alchohol.post.controller.Response;

import com.example.alchohol.post.models.dto.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NewsFeedResponse {
    private Post post;
}
