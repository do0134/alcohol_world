package com.example.alchohol.user.repository;

import com.example.alchohol.user.model.entity.FollowEntity;
import com.example.alchohol.user.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
    Optional<FollowEntity> findByFollowerAndFollowing(UserEntity follower, UserEntity following);
}
