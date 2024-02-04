package com.example.activity_service.model.dto.response;

import com.example.activity_service.model.dto.Comment;
import com.example.activity_service.model.dto.Like;
import com.example.activity_service.model.dto.Post;
import com.example.user_service.model.dto.PostUserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PostResponse {
    private PostUserDto user;
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
