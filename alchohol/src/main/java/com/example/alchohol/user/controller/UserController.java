package com.example.alchohol.user.controller;

import com.example.alchohol.common.response.Response;
import com.example.alchohol.user.controller.request.*;
import com.example.alchohol.user.controller.response.*;
import com.example.alchohol.user.model.dto.User;
import com.example.alchohol.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping ("/{userId}")
    public Response<UserProfileResponse> readProfile(@PathVariable("userId") Long userId) {
        User user = userService.userProfile(userId);
        return Response.success(new UserProfileResponse(user.getUserEmail(), user.getNickname(), user.getStatement(), user.getUserImage()));
    }

    @PutMapping("/{userId}")
    public Response<UserProfileResponse> updateProfile(@PathVariable("userId") Long userId, @ModelAttribute UserJoinRequest userJoinRequest, @AuthenticationPrincipal User user) {
        User nowUser = userService.updateUserProfile(
                userId, user.getUserEmail(), userJoinRequest.getNickname(), userJoinRequest.getStatement(),
                Optional.ofNullable(userJoinRequest.getUserImage())
        );

        return Response.success(new UserProfileResponse(nowUser.getUserEmail(),nowUser.getNickname(),nowUser.getStatement(),nowUser.getUserImage()));
    }

}
