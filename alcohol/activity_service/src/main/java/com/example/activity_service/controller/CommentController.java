package com.example.activity_service.controller;


import com.example.activity_service.model.dto.request.CommentRequest;
import com.example.activity_service.service.CommentService;
import com.example.common.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{postPk}")
    public Response<Void> createComment(Long userId, @PathVariable("postPk") Long postId, @RequestBody CommentRequest commentRequest) {
        commentService.createComment(userId, postId, commentRequest.getContent());
        return Response.success();
    }
}
