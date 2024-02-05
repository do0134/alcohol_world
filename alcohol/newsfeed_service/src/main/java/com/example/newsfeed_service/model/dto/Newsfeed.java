package com.example.newsfeed_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Newsfeed {
    private PostUserDto user;
    private Long activityId;
    private String title;
    private String content;
    private Long commentCount;
    private Long likeCount;
}
