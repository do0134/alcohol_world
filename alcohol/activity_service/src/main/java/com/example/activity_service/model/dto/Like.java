package com.example.activity_service.model.dto;

import com.example.activity_service.model.entity.CommentLikeEntity;
import com.example.activity_service.model.entity.PostLikeEntity;
import com.example.user_service.model.dto.PostUserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Like {
    private PostUserDto likeUser;

    public static Like fromEntity(PostLikeEntity postLikeEntity) {
        return new Like(PostUserDto.fromEntity(postLikeEntity.getUser()));
    }

    public static Like fromEntity(CommentLikeEntity commentLikeEntity) {
        return new Like(PostUserDto.fromEntity(commentLikeEntity.getUser()));
    }
}
