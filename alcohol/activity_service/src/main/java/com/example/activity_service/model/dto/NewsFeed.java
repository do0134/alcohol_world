package com.example.activity_service.model.dto;

import com.example.activity_service.model.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NewsFeed {
    private PostUserDto user;
    private Long activityId;
    private String title;
    private String content;
    private Long commentCount;
    private Long likeCount;

    public static NewsFeed fromEntity(PostEntity postEntity, PostUserDto postUserDto, Long activityId) {
        return new NewsFeed(
                postUserDto,
                activityId,
                postEntity.getTitle(),
                postEntity.getContent(),
                (long)postEntity.getCommentList().size(),
                (long)postEntity.getPostLikeList().size()
        );
    }
}
