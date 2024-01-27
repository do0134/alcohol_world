package com.example.alchohol.user.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserProfileResponse {
    String userEmail;
    String nickname;
    String statement;
    String userImage;
}
