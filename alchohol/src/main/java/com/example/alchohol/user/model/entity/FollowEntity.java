package com.example.alchohol.user.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "follow")
@Getter
@Setter
public class FollowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private UserEntity follower;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private UserEntity following;

    public static FollowEntity toEntity(UserEntity follower, UserEntity following) {
        FollowEntity followEntity = new FollowEntity();
        followEntity.setFollower(follower);
        followEntity.setFollowing(following);

        return followEntity;
    }
}
