package com.example.alchohol.post.models.dto;

import com.example.alchohol.post.models.entity.PostEntity;
import com.example.alchohol.user.model.dto.PostUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedPost {
    private PostUser user;
    private String title;
    private String content;
    private Long countComments;
    private Long countLikes;
    private Timestamp createdAt;

    public static FeedPost fromEntity(PostEntity postEntity) {
        PostUser user = PostUser.fromEntity(postEntity.getUser());
        return new FeedPost(
                user,
                postEntity.getTitle(),
                postEntity.getContent(),
                (long)postEntity.getCommentList().size(),
                (long)postEntity.getPostLikeList().size(),
                postEntity.getCreatedAt()
        );
    }

}
