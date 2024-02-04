package com.example.activity_service.controller;


import com.example.activity_service.model.dto.request.CommentRequest;
import com.example.activity_service.service.CommentService;
import com.example.common.response.Response;
import com.example.user_service.model.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{postPk}")
    public Response<Void> createComment(@AuthenticationPrincipal User user, @PathVariable("postPk") Long postId, @RequestBody CommentRequest commentRequest) {
        commentService.createComment(user.getUserEmail(), postId, commentRequest.getContent());
        return Response.success();
    }
}
