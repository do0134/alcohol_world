package com.example.user_service.model.entity;

import com.example.user_service.model.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;


@Entity
@Table(name = "User")
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Lob
    @Column(name = "statement")
    private String statement;

    @Column(name = "user_image")
    private String userImage;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.CUSTOMER;

    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

    public static UserEntity toEntity(String userEmail, String password, String nickname, String statement, String userImage) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserEmail(userEmail);
        userEntity.setPassword(password);
        userEntity.setNickname(nickname);
        userEntity.setStatement(statement);
        userEntity.setUserImage(userImage);
        return userEntity;
    }
}
