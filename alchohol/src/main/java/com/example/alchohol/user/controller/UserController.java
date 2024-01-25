package com.example.alchohol.user.controller;

import com.example.alchohol.common.request.UserJoinRequest;
import com.example.alchohol.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public void signup(@RequestBody UserJoinRequest userJoinRequest) {
        userService.signup(userJoinRequest.getUserEmail(),userJoinRequest.getPassword(),userJoinRequest.getNickname(),userJoinRequest.getStatement(),userJoinRequest.getUserImage());

    }


}
