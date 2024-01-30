package com.example.alchohol.post.models.dto;

import com.example.alchohol.post.models.entity.CommentEntity;
import com.example.alchohol.user.model.dto.PostUser;
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
    private PostUser user;

    public static Comment fromEntity(CommentEntity commentEntity) {
        return new Comment(
                commentEntity.getContent(),
                PostUser.fromEntity(commentEntity.getUser())
        );
    }
}
