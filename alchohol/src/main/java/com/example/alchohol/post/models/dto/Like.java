package com.example.alchohol.post.models.dto;

import com.example.alchohol.post.models.entity.CommentLikeEntity;

import com.example.alchohol.post.models.entity.PostLikeEntity;
import com.example.alchohol.user.model.dto.PostUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Like {
    private PostUser likeUser;

    public static Like fromEntity(PostLikeEntity postLikeEntity) {
        return new Like(PostUser.fromEntity(postLikeEntity.getUser()));
    }

    public static Like fromEntity(CommentLikeEntity commentLikeEntity) {
        return new Like(PostUser.fromEntity(commentLikeEntity.getUser()));
    }
}
