package com.example.user_service.controller.internal;

import com.example.common.response.Response;
import com.example.user_service.model.dto.PostUserDto;

import com.example.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/internal/user")
public class UserInternalController {

    private final UserService userService;
    @GetMapping("/{userId}")
    public Response<PostUserDto> getUser(@PathVariable Long userId) {
        PostUserDto postUser = userService.getPostUser(userId);
        return Response.success(postUser);
    }
}
