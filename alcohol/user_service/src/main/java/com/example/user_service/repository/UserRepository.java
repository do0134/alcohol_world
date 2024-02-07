package com.example.user_service.repository;

import com.example.user_service.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserEmail(String userEmail);
    Optional<UserEntity> findByNickname(String nickname);
}
