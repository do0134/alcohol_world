package com.example.activity_service.repository;

import com.example.activity_service.model.entity.FollowEntity;
import com.example.user_service.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
    Optional<FollowEntity> findByFollowerAndFollowing(UserEntity follower, UserEntity following);
    Optional<List<FollowEntity>> findAllByFollower(UserEntity follower);
    Optional<List<FollowEntity>> findAllByFollowing (UserEntity following);
}
