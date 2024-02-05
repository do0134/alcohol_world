package com.example.activity_service.repository;

import com.example.activity_service.model.entity.CommentEntity;
import com.example.activity_service.model.entity.CommentLikeEntity;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, Long> {
    Optional<CommentLikeEntity> findByUserAndComment(Long userId, CommentEntity comment);

    Long countByComment(CommentEntity comment);

    @Transactional
    void deleteAllByComment(@Param("comment")CommentEntity comment);
}
