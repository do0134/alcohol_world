package com.example.activity_service.repository;

import com.example.activity_service.model.entity.PostEntity;
import com.example.activity_service.model.entity.PostLikeEntity;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Long> {
    Optional<PostLikeEntity> findByUserIdAndPost(Long userId, PostEntity post);

    Long countByPost(PostEntity post);

    @Transactional
    void deleteAllByPost(@Param("post")PostEntity post);
}
