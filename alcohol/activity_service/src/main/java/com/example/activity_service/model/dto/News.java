package com.example.activity_service.model.dto;

import com.example.activity_service.model.ActivityType;
import com.example.activity_service.model.entity.ActivityEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class News {
    private String message;
    private ActivityType activityType;
    private Long userId;
    private Long targetId;

    public static News fromEntity(ActivityEntity activityEntity, String message) {
        ActivityType activity = activityEntity.getActivityType();
        Long target = 0L;

        if (activity.equals(ActivityType.COMMENT)) {
            target = activityEntity.getComment().getId();
        } else if (activity.equals(ActivityType.FOLLOW)) {
            target = activityEntity.getFollow().getFollowing();
        } else if (activity.equals(ActivityType.POST_LIKE)) {
            target = activityEntity.getPostLikeEntity().getId();
        } else {
            target = activityEntity.getCommentLikeEntity().getId();
        }

        return new News(
                message,
                activity,
                activityEntity.getUserId(),
                target
        );
    }
}
