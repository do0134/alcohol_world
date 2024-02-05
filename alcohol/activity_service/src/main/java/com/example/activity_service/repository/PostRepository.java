package com.example.activity_service.repository;

import com.example.activity_service.model.entity.PostEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    @Query("SELECT p FROM PostEntity p WHERE p.userId in :FollowingIds")
    Optional<List<PostEntity>> findAllByUserInFollowingIds(@Param("FollowingIds") List<Long> FollowingIds);
}
