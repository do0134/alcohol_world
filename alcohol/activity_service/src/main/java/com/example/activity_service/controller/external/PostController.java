package com.example.activity_service.controller.external;

import com.example.activity_service.model.dto.request.PostRequest;
import com.example.activity_service.model.dto.response.PostResponse;
import com.example.activity_service.service.LikeService;
import com.example.activity_service.service.PostService;
import com.example.common.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;
    private final LikeService likeService;

    @GetMapping("/{postPk}")
    public Response<PostResponse> getPost(@PathVariable("postPk") Long postId) {
        PostResponse post = postService.getPost(postId);
        return Response.success(post);
    }

    @PostMapping("/")
    public Response<Void> createPost(Long userId, @RequestBody PostRequest postRequest) {
        postService.createPost(userId,postRequest.getTitle(), postRequest.getContent());
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
    private Response<Void> postLike(Long userId,@PathVariable("postPk") Long postId) {
        likeService.postLike(userId, postId);
        return Response.success();
    }
}
