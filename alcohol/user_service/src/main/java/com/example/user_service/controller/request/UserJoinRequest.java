package com.example.user_service.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class UserJoinRequest {
    private String userEmail;
    private String password;
    private String nickname;
    private String statement;
    private MultipartFile userImage;

    public UserJoinRequest(String email, String password, String nickname, String statement, MultipartFile userImage) {
        this.userEmail = email;
        this.password = password;
        this.nickname = nickname;
        this.statement = statement;
        this.userImage = userImage;
    }
}
