package com.example.alchohol.post.repository;

import com.example.alchohol.post.models.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @Query("SELECT c FROM CommentEntity c WHERE c.user.id in :FollowingIds")
    List<CommentEntity> findAllByUserInFollowingIds(@Param("FollowingIds") List<Long> FollowingIds);
}
