package com.example.alchohol.post.repository;

import com.example.alchohol.post.models.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    @Query("SELECT p FROM PostEntity p WHERE p.user.id in :FollowingIds")
    List<PostEntity> findAllByUserInFollowingIds(@Param("FollowingIds") List<Long> FollowingIds);

}
