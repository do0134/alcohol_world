package com.example.alchohol.post.models.dto;

import com.example.alchohol.post.models.entity.CommentEntity;
import com.example.alchohol.post.models.entity.PostEntity;
import com.example.alchohol.post.models.entity.PostLikeEntity;
import com.example.alchohol.user.model.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {
    private UserEntity user;
    private String title;
    private String content;
    private List<CommentEntity> commentList;
    private List<PostLikeEntity> likeList;


    public static Post fromEntity(PostEntity postEntity) {
        return new Post(
                postEntity.getUser(),
                postEntity.getTitle(),
                postEntity.getContent(),
                postEntity.getCommentList(),
                postEntity.getPostLikeList()
        );
    }
}
