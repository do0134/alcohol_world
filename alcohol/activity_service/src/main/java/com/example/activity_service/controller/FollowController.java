package com.example.activity_service.controller;

import com.example.activity_service.service.FollowService;
import com.example.common.response.Response;
import com.example.user_service.model.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follow")
public class FollowController {
    private final FollowService followService;

    @PostMapping("/{followerId}")
    public Response<Void> follow(@AuthenticationPrincipal User user, @PathVariable("followerId") Long followerId) {
        followService.userFollow(user.getUserEmail(), followerId);
        return Response.success();
    }
}
