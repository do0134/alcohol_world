package com.example.activity_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostUserDto {
    private String userEmail;
    private String nickname;
    private String userImage;
}
