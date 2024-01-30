package com.example.alchohol.user.repository;

import com.example.alchohol.user.model.entity.FollowEntity;
import com.example.alchohol.user.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
    Optional<FollowEntity> findByFollowerAndFollowing(UserEntity follower, UserEntity following);
    Optional<List<FollowEntity>> findAllByFollower(UserEntity follower);
    Optional<List<FollowEntity>> findAllByFollowing (UserEntity following);

    @Query("SELECT f FROM FollowEntity f WHERE f.follower.id in :FollowingIds")
    List<FollowEntity> findAllByUserInFollowingIds(@Param("FollowingIds") List<Long> FollowingIds);
}
