package com.example.activity_service.model.entity;

import com.example.activity_service.model.ActivityType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Activity")
@Getter
@Setter
public class ActivityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    @NotNull
    private Long userId;

    @Column(name = "activity_type")
    @Enumerated(EnumType.STRING)
    @NotNull
    private ActivityType activityType;

    @OneToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @OneToOne
    @JoinColumn(name = "comment_id")
    private CommentEntity comment;

    @OneToOne
    @JoinColumn(name = "post_like_id")
    private PostLikeEntity postLikeEntity;

    @OneToOne
    @JoinColumn(name = "comment_like_id")
    private CommentLikeEntity commentLikeEntity;

    @OneToOne
    @JoinColumn(name = "follow_id")
    private FollowEntity follow;

    public static ActivityEntity toEntity(ActivityType activityType, PostEntity post, Long userId) {
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setUserId(userId);
        activityEntity.setActivityType(activityType);
        activityEntity.setPost(post);
        return activityEntity;
    }

    public static ActivityEntity toEntity(ActivityType activityType, PostLikeEntity postLike, Long userId) {
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setUserId(userId);
        activityEntity.setActivityType(activityType);
        activityEntity.setPostLikeEntity(postLike);
        return activityEntity;
    }

    public static ActivityEntity toEntity(ActivityType activityType, CommentEntity comment, Long userId) {
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setUserId(userId);
        activityEntity.setActivityType(activityType);
        activityEntity.setComment(comment);
        return activityEntity;
    }

    public static ActivityEntity toEntity(ActivityType activityType, CommentLikeEntity commentLike, Long userId) {
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setUserId(userId);
        activityEntity.setActivityType(activityType);
        activityEntity.setCommentLikeEntity(commentLike);
        return activityEntity;
    }

    public static ActivityEntity toEntity(ActivityType activityType, FollowEntity follow) {
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setUserId(follow.getFollower());
        activityEntity.setActivityType(activityType);
        activityEntity.setFollow(follow);
        return activityEntity;
    }
}
