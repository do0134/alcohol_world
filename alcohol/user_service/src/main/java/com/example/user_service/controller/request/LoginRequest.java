package com.example.user_service.controller.request;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {
    private String userEmail;
    private String password;
    private String deviceId;

    public LoginRequest(String userEmail, String password, String deviceId) {
        this.userEmail = userEmail;
        this.password = password;
        this.deviceId = deviceId;
    }
}
