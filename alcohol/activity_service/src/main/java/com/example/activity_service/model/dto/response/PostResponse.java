package com.example.activity_service.model.dto.response;

import com.example.activity_service.model.dto.Comment;
import com.example.activity_service.model.dto.Post;
import com.example.activity_service.model.dto.PostUserDto;
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
    private Long likeCount;

    public static PostResponse fromPost(Post post, List<Comment> comments, Long likeCount) {
        Long commentCount = (long)comments.size();

        return new PostResponse(
                post.getPostUserDto(),
                post.getTitle(),
                post.getContent(),
                comments,
                commentCount,
                likeCount
        );
    }
}
