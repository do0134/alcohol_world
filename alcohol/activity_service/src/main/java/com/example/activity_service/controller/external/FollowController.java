package com.example.activity_service.controller.external;

import com.example.activity_service.service.FollowService;
import com.example.common.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follow")
public class FollowController {
    private final FollowService followService;

    @PostMapping("/{followerId}/{followingId}")
    public Response<Void> follow(@PathVariable("followerId") Long followerId, @PathVariable("followingId") Long followingId) {
        followService.userFollow(followerId, followingId);
        return Response.success();
    }
}
