package com.example.alchohol.post.models.dto;

import com.example.alchohol.post.models.entity.CommentEntity;
import com.example.alchohol.post.models.entity.PostEntity;
import com.example.alchohol.post.models.entity.PostLikeEntity;
import com.example.alchohol.user.model.dto.PostUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {
    private PostUser user;
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
                 PostUser.fromEntity(postEntity.getUser()),
                postEntity.getTitle(),
                postEntity.getContent(),
                comments,
                likes
        );
    }
}
