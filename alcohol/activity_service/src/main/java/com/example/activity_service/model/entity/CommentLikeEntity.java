package com.example.activity_service.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "comment_like")
@Getter
@Setter
public class CommentLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentEntity comment;

    private Timestamp createdAt;

    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
    }

    public static CommentLikeEntity toEntity(Long userId, CommentEntity comment) {
        CommentLikeEntity commentLikeEntity = new CommentLikeEntity();
        commentLikeEntity.setUserId(userId);
        commentLikeEntity.setComment(comment);
        return commentLikeEntity;
    }
}