package com.example.alchohol.post.repository;

import com.example.alchohol.post.models.entity.PostEntity;
import com.example.alchohol.post.models.entity.PostLikeEntity;
import com.example.alchohol.user.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Long> {
    Optional<PostLikeEntity> findByUserAndPost(UserEntity user, PostEntity post);

    Long countByPost(PostEntity post);

    @Query("SELECT p FROM PostLikeEntity p WHERE p.user.id in :FollowingIds")
    List<PostLikeEntity> findAllByUserInFollowingIds(@Param("FollowingIds") List<Long> FollowingIds);

    @Transactional
    void deleteAllByPost(@Param("post")PostEntity post);
}
