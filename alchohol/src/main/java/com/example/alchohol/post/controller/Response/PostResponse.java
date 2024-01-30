package com.example.alchohol.post.controller.Response;


import com.example.alchohol.post.models.dto.Comment;
import com.example.alchohol.post.models.dto.Like;
import com.example.alchohol.post.models.dto.Post;
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

    public static PostResponse fromPost(Post post) {
        Long commentCount = (long)post.getCommentList().size();
        Long likeCount = (long)post.getLikeList().size();
        return new PostResponse(
                post.getUser(),
                post.getTitle(),
                post.getContent(),
                post.getCommentList(),
                commentCount,
                post.getLikeList(),
                likeCount
        );
    }
}
