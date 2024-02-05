package com.example.activity_service.model.dto;

import com.example.activity_service.model.entity.CommentEntity;
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
    private PostUserDto postUser;

    public static Comment fromEntityAndFeign(CommentEntity commentEntity, PostUserDto postUser) {

        return new Comment(
                commentEntity.getContent(),
                postUser
        );
    }
}
