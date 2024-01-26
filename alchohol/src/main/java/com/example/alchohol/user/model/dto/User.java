package com.example.alchohol.user.model.dto;

import com.example.alchohol.user.model.entity.UserEntity;
import com.example.alchohol.user.model.entity.UserRole;
import lombok.*;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private Long id;
    private String userEmail;
    private String password;
    private String nickname;
    private String statement;
    private String userImage;
    private Timestamp registerAt;
    private Timestamp updatedAt;
    private UserRole userRole;

    public static User fromEntity(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getUserEmail(),
                userEntity.getPassword(),
                userEntity.getNickname(),
                userEntity.getStatement(),
                userEntity.getUserImage(),
                userEntity.getCreatedAt(),
                userEntity.getUpdatedAt(),
                userEntity.getUserRole()
        );
    }
}
