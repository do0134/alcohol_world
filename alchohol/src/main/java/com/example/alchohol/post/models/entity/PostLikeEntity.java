package com.example.alchohol.post.models.entity;

import com.example.alchohol.user.model.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;


@Entity
@Table(name = "post_like")
@Getter
@Setter
@NoArgsConstructor
public class PostLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private PostEntity post;


    private Timestamp createdAt;

    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
    }

    public static PostLikeEntity toEntity(UserEntity user, PostEntity post) {
        PostLikeEntity postLikeEntity = new PostLikeEntity();
        postLikeEntity.setUser(user);
        postLikeEntity.setPost(post);
        return postLikeEntity;
    }
}
