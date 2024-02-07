package com.example.activity_service.model.dto;

import com.example.activity_service.model.entity.CommentLikeEntity;
import com.example.activity_service.model.entity.PostLikeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Like {
    PostUserDto postUser;

    public static Like fromFeign(PostUserDto postUser) {
        return new Like(postUser);
    }
}
