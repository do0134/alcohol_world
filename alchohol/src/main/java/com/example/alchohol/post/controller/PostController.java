package com.example.alchohol.post.controller;

import com.example.alchohol.common.response.Response;
import com.example.alchohol.post.controller.Request.PostRequest;

import com.example.alchohol.post.controller.Response.NewsFeedResponse;
import com.example.alchohol.post.controller.Response.PostResponse;
import com.example.alchohol.post.models.dto.Activate;
import com.example.alchohol.post.models.dto.Post;
import com.example.alchohol.post.service.LikeService;
import com.example.alchohol.post.service.PostService;
import com.example.alchohol.user.model.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;
    private final LikeService likeService;

    @GetMapping("/")
    public Response<NewsFeedResponse> getPosts(@AuthenticationPrincipal User user) {
        List<Activate> newsFeed = postService.getNewsFeed(user.getUserEmail());
        return Response.success(new NewsFeedResponse(newsFeed));
    }

    @GetMapping("/{postPk}")
    public Response<PostResponse> getPost(@PathVariable("postPk") Long postId) {
        PostResponse post = postService.getPost(postId);
        return Response.success(post);
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

    @PostMapping("/{postPk}/likes")
    private Response<Void> postLike(@AuthenticationPrincipal User user,@PathVariable("postPk") Long postId) {
        likeService.postLike(user.getUserEmail(), postId);
        return Response.success();
    }
}