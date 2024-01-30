package com.example.alchohol.user.model.dto;

import com.example.alchohol.user.model.entity.UserEntity;
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
public class PostUser {
    private Long id;
    private String nickname;
    private String userImage;

    public static PostUser fromEntity(UserEntity userEntity) {
        return new PostUser(
                userEntity.getId(),
                userEntity.getNickname(),
                userEntity.getUserImage()
                );
    }
}
