package com.example.activity_service.model.dto;

import com.example.activity_service.model.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Post {
    private PostUserDto postUserDto;

    private String title;
    private String content;

    public static Post fromEntityAndFeign(PostEntity postEntity, PostUserDto postUser) {
        return new Post(
                postUser,
                postEntity.getTitle(),
                postEntity.getContent()
        );
    }
}
