package com.example.alchohol.user.controller;

import com.example.alchohol.common.response.Response;
import com.example.alchohol.user.controller.request.LoginRequest;
import com.example.alchohol.user.controller.request.UserJoinRequest;
import com.example.alchohol.user.controller.response.LoginResponse;
import com.example.alchohol.user.controller.response.UserJoinResponse;
import com.example.alchohol.user.model.dto.User;
import com.example.alchohol.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public Response<UserJoinResponse> signup(@ModelAttribute UserJoinRequest userJoinRequest) {
        User user = userService.signup(userJoinRequest.getUserEmail(),userJoinRequest.getPassword(),userJoinRequest.getNickname(),userJoinRequest.getStatement(),userJoinRequest.getUserImage());
        return Response.success(UserJoinResponse.fromUser(user));
    }

    @PostMapping("/login")
    public Response<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String userEmail = loginRequest.getUserEmail();
        String password = loginRequest.getPassword();
        String token = userService.userLogin(userEmail, password);

        return Response.success(new LoginResponse(token));
    }
}
