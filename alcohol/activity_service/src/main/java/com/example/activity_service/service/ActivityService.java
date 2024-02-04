package com.example.activity_service.service;

import com.example.activity_service.model.ActivityType;
import com.example.activity_service.model.entity.*;
import com.example.activity_service.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;

    @Transactional
    public void saveFollowActivity(FollowEntity followEntity) {
        activityRepository.save(ActivityEntity.toEntity(ActivityType.FOLLOW, followEntity));
    }

    @Transactional
    public void savePostActivity(PostEntity postEntity) {
        activityRepository.save(ActivityEntity.toEntity(ActivityType.POST, postEntity));
    }

    @Transactional
    public void savePostLikeActivity(PostLikeEntity postLikeEntity) {
        activityRepository.save(ActivityEntity.toEntity(ActivityType.POST_LIKE, postLikeEntity));
    }

    @Transactional
    public void saveCommentActivity(CommentEntity commentEntity) {
        activityRepository.save(ActivityEntity.toEntity(ActivityType.COMMENT, commentEntity));
    }

    @Transactional
    public void saveCommentLikeActivity(CommentLikeEntity commentLikeEntity) {
        activityRepository.save(ActivityEntity.toEntity(ActivityType.COMMENT_LIKE, commentLikeEntity));
    }
}
