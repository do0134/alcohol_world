package com.example.user_service.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class UserModifyRequest {
    private String userEmail;
    private String nickname;
    private String statement;
    private MultipartFile userImage;

    public UserModifyRequest(String email, String nickname, String statement, MultipartFile userImage) {
        this.userEmail = email;
        this.nickname = nickname;
        this.statement = statement;
        this.userImage = userImage;
    }
}
