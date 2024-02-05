package com.example.newsfeed_service.model.dto;

import com.example.newsfeed_service.model.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class News {
    private String message;
    private ActivityType activityType;
    private Long userId;
    private Long targetId;
}
