package com.example.activity_service.repository;

import com.example.activity_service.model.entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
    Optional<FollowEntity> findByFollowerAndFollowing(Long follower, Long following);
    Optional<List<FollowEntity>> findAllByFollower(Long follower);
    Optional<List<FollowEntity>> findAllByFollowing (Long following);

    @Query("SELECT f.following FROM FollowEntity f WHERE f.follower = :follower")
    Optional<List<Long>> findAllFollowingByFollower(@Param("follower") Long follower);
}
