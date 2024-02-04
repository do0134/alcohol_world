package com.example.activity_service.model.dto;

import com.example.activity_service.model.entity.CommentEntity;
import com.example.user_service.model.dto.PostUserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Comment {
    private String content;
    private PostUserDto user;

    public static Comment fromEntity(CommentEntity commentEntity) {
        return new Comment(
                commentEntity.getContent(),
                PostUserDto.fromEntity(commentEntity.getUser())
        );
    }
}
