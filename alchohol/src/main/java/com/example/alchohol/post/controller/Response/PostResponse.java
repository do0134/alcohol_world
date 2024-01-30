package com.example.alchohol.post.controller.Response;


import com.example.alchohol.post.models.dto.Post;
import com.example.alchohol.post.models.entity.CommentEntity;
import com.example.alchohol.post.models.entity.PostLikeEntity;
import com.example.alchohol.user.model.dto.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PostResponse {
    private User user;
    private String title;
    private String content;
    private List<CommentEntity> commentList;
    private Long commentCount;
    private List<PostLikeEntity> likeList;
    private Long likeCount;

}
