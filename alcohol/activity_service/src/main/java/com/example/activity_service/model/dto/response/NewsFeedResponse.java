package com.example.activity_service.model.dto.response;

import com.example.activity_service.model.dto.PostUserDto;
import com.example.activity_service.model.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NewsFeedResponse {
    private PostUserDto user;
    private Long activityId;
    private String title;
    private String content;
    private Long commentCount;
    private Long likeCount;

    public static NewsFeedResponse fromEntity(PostEntity postEntity, PostUserDto postUserDto, Long activityId) {
        return new NewsFeedResponse(
                postUserDto,
                activityId,
                postEntity.getTitle(),
                postEntity.getContent(),
                (long)postEntity.getCommentList().size(),
                (long)postEntity.getPostLikeList().size()
        );
    }
}
