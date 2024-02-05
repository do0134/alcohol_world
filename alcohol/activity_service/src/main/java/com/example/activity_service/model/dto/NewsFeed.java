package com.example.activity_service.model.dto;

import com.example.activity_service.model.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class NewsFeed {
    private PostUserDto user;
    private String title;
    private String content;
    private Timestamp createdAt;
    private Long commentCount;
    private Long likeCount;

    public static NewsFeed fromEntity(PostEntity postEntity, PostUserDto postUserDto) {
        return new NewsFeed(
                postUserDto,
                postEntity.getTitle(),
                postEntity.getContent(),
                postEntity.getCreatedAt(),
                (long)postEntity.getCommentList().size(),
                (long)postEntity.getPostLikeList().size()
        );
    }
}
