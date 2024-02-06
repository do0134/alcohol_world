package com.example.user_service.controller.external;

import com.example.common.response.Response;
import com.example.user_service.model.dto.request.LoginRequest;
import com.example.user_service.model.dto.request.LogoutRequest;
import com.example.user_service.model.dto.request.UserJoinRequest;
import com.example.user_service.model.dto.response.LoginResponse;
import com.example.user_service.model.dto.response.UserJoinResponse;
import com.example.user_service.model.dto.User;
import com.example.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/signup")
    public Response<UserJoinResponse> signup(@ModelAttribute UserJoinRequest userJoinRequest) {
        System.out.println("say hi!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        User user = userService.signup(userJoinRequest.getUserEmail(),userJoinRequest.getPassword(),userJoinRequest.getNickname(),userJoinRequest.getStatement(),userJoinRequest.getUserImage());
        return Response.success(UserJoinResponse.fromUser(user));
    }

    @PostMapping("/login")
    public Response<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = userService.userLogin(loginRequest.getUserEmail(), loginRequest.getPassword(), loginRequest.getDeviceId());

        return Response.success(new LoginResponse(token));
    }

    @PostMapping("/logout")
    public Response<Void> logout(@AuthenticationPrincipal User user, @RequestBody LogoutRequest logoutRequest) {
        userService.logout(user.getUserEmail(), logoutRequest.getDeviceId());
        return Response.success();
    }
}
