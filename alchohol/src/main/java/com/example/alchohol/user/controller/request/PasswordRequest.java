package com.example.alchohol.user.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PasswordRequest {
    private String password;

    public PasswordRequest(String password) {
        this.password = password;
    }
}
