package com.example.alchohol.post.controller;

import com.example.alchohol.common.response.Response;
import com.example.alchohol.post.controller.Request.PostRequest;
import com.example.alchohol.post.service.PostService;
import com.example.alchohol.user.model.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/")
    public Response<Void> getPosts() {
        return Response.success();
    }

    @PostMapping("/")
    public Response<Void> createPost(@AuthenticationPrincipal User user, @RequestBody PostRequest postRequest) {
        postService.createPost(user.getUserEmail(),postRequest.getTitle(), postRequest.getContent());
        return Response.success();
    }

    @PutMapping("/{postPk}")
    public Response<Void> updatePost(@PathVariable("postPk") Long postId) {
        return Response.success();
    }

    @DeleteMapping("/{postPk}")
    public Response<Void> deletePost(@PathVariable("postPk") Long postId) {
        return Response.success();
    }
}
