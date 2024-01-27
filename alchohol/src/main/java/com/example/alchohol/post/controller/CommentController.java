package com.example.alchohol.post.controller;

import com.example.alchohol.common.response.Response;
import com.example.alchohol.post.controller.Request.CommentRequest;
import com.example.alchohol.post.service.CommentService;
import com.example.alchohol.user.model.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postPk}")
    public Response<Void> createComment(@AuthenticationPrincipal User user, @PathVariable("postPk") Long postId, @RequestBody CommentRequest commentRequest) {
        commentService.createComment(user.getUserEmail(), postId, commentRequest.getContent());
        return Response.success();
    }
}
