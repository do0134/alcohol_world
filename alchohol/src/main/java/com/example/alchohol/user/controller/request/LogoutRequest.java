package com.example.alchohol.user.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LogoutRequest {
    private String deviceId;

    public LogoutRequest(String deviceId) {
        this.deviceId = deviceId;
    }
}
