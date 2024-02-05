package com.example.user_service.controller.external;

import com.example.common.response.Response;
import com.example.user_service.model.dto.request.PasswordRequest;
import com.example.user_service.model.dto.request.UserModifyRequest;
import com.example.user_service.model.dto.response.UserProfileResponse;
import com.example.user_service.model.dto.User;
import com.example.user_service.service.UserService;
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


    @GetMapping("/{userId}")
    public Response<UserProfileResponse> readProfile(@PathVariable("userId") Long userId) {
        User user = userService.userProfile(userId);
        return Response.success(new UserProfileResponse(user.getUserEmail(), user.getNickname(), user.getStatement(), user.getUserImage()));
    }

    @PutMapping("/{userId}")
    public Response<UserProfileResponse> updateProfile(@PathVariable("userId") Long userId, @ModelAttribute UserModifyRequest userModifyRequest, @AuthenticationPrincipal User user) {
        User nowUser = userService.updateUserProfile(
                userId, user.getUserEmail(), userModifyRequest.getNickname(), userModifyRequest.getStatement(),
                Optional.ofNullable(userModifyRequest.getUserImage())
        );

        return Response.success(new UserProfileResponse(nowUser.getUserEmail(),nowUser.getNickname(),nowUser.getStatement(),nowUser.getUserImage()));
    }

    @PutMapping("/{userId}/password")
    public Response<Void> updateProfile(@PathVariable("userId") Long userId, @RequestBody PasswordRequest passwordRequest, @AuthenticationPrincipal User user) {
        userService.updatePassword(userId, user.getUserEmail(), passwordRequest.getPassword());
        return Response.success();
    }

}
