package com.example.newsfeed_service.model.dto;

import com.example.newsfeed_service.model.ActivityType;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class News {
    private String message;
    private ActivityType activityType;
    private Long userId;
    private Long targetId;
}
