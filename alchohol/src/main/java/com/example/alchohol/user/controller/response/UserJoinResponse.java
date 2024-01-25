package com.example.alchohol.user.controller.response;

import com.example.alchohol.user.dto.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserJoinResponse {
    private Long id;
    private String email;
    private String nickname;

    public static UserJoinResponse fromUser(User user) {
        return new UserJoinResponse(
                user.getId(),
                user.getUserEmail(),
                user.getNickname()
        );
    }
}
