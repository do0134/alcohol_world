package com.example.alchohol.user.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {
    private String userEmail;
    private String password;

    public LoginRequest(String userEmail, String password) {
        this.userEmail = userEmail;
        this.password = password;
    }
}
