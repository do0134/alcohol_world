package com.example.alchohol.user.controller;

import com.example.alchohol.common.response.Response;
import com.example.alchohol.user.controller.request.*;
import com.example.alchohol.user.controller.response.*;
import com.example.alchohol.user.model.dto.User;
import com.example.alchohol.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    // TODO: UserUpdateRequest에 isPicture을 추가해서 사진 받았는지 여부를 확인할 것
    // TODO: 지금 Image 파일 저장할 때, 이름 저장 로직이 잘못됨. 기존 파일의 이름으로 저장
    // TODO: 무슨 이유인지 모르겠는데 토큰 인가를 건드리다가 모든 Error가 401로 나옴 해결이 필요함
    @PutMapping("/{userId}")
<<<<<<< HEAD
    public Response<UserProfileResponse> updateProfile(@PathVariable("userId") Long userId, @ModelAttribute UserJoinRequest userJoinRequest) {

        User user = userService.updateUserProfile(
                userId, Optional.ofNullable(userJoinRequest.getPassword()),
                userJoinRequest.getNickname(), userJoinRequest.getStatement(),
                Optional.ofNullable(userJoinRequest.getUserImage())
        );
        return Response.success(new UserProfileResponse(user.getUserEmail(),user.getNickname(),user.getStatement(),user.getUserImage()));
=======
    public Response<UserProfileResponse> updateProfile(@PathVariable("userId") Long userId, @ModelAttribute UserJoinRequest userJoinRequest, @AuthenticationPrincipal User user) {
        User nowUser = userService.updateUserProfile(
                userId, user.getUserEmail(), userJoinRequest.getNickname(), userJoinRequest.getStatement(),
                Optional.ofNullable(userJoinRequest.getUserImage())
        );

        return Response.success(new UserProfileResponse(nowUser.getUserEmail(),nowUser.getNickname(),nowUser.getStatement(),nowUser.getUserImage()));
>>>>>>> parent of 7fb8fd0 (hotfix: 비밀번호 수정 분리)
    }

}
