package com.example.activity_service.model.dto;

import com.example.activity_service.model.entity.CommentEntity;
import com.example.activity_service.model.entity.PostEntity;
import com.example.activity_service.model.entity.PostLikeEntity;
import com.example.user_service.model.dto.PostUserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Post {
    private PostUserDto user;
    private String title;
    private String content;
    private List<Comment> commentList;
    private List<Like> likeList;

    public static Post fromEntity(PostEntity postEntity) {
        List<Comment> comments = new ArrayList<>();
        List<Like> likes = new ArrayList<>();

        for (CommentEntity comment: postEntity.getCommentList()) {
            Comment newComment = Comment.fromEntity(comment);
            comments.add(newComment);
        }

        for (PostLikeEntity postLikeEntity: postEntity.getPostLikeList()) {
            Like newLike = Like.fromEntity(postLikeEntity);
            likes.add(newLike);
        }

        return new Post(
                PostUserDto.fromEntity(postEntity.getUser()),
                postEntity.getTitle(),
                postEntity.getContent(),
                comments,
                likes
        );
    }
}
