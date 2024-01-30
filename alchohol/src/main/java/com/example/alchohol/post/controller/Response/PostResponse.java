package com.example.alchohol.post.controller.Response;


import com.example.alchohol.post.models.dto.Comment;
import com.example.alchohol.post.models.dto.Like;
import com.example.alchohol.user.model.dto.PostUser;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PostResponse {
    private PostUser user;
    private String title;
    private String content;
    private List<Comment> commentList;
    private Long commentCount;
    private List<Like> likeList;
    private Long likeCount;

}
