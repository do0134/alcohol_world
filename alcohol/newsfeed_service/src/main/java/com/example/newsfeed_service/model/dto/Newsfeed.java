package com.example.newsfeed_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class Newsfeed {
    private PostUserDto user;
    private String title;
    private String content;
    private Timestamp createdAt;
    private Long commentCount;
    private Long likeCount;
}
