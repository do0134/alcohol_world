package com.example.user_service.model.dto;

import com.example.user_service.model.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostUserDto {
    private Long id;
    private String nickname;
    private String userImage;

    public static PostUserDto fromEntity(UserEntity userEntity) {
        return new PostUserDto(
                userEntity.getId(),
                userEntity.getNickname(),
                userEntity.getUserImage()
        );
    }
}
