package com.example.alchohol.user.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserJoinRequest {
    private String userEmail;
    private String password;
    private String nickname;
    private String statement;
    private String userImage;

    public UserJoinRequest(String email, String password, String nickname, String statement, String userImage) {
        this.userEmail = email;
        this.password = password;
        this.nickname = nickname;
        this.statement = statement;
        this.userImage = userImage;
    }
}
