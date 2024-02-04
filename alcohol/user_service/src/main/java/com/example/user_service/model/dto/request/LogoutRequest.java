package com.example.user_service.model.dto.request;

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
