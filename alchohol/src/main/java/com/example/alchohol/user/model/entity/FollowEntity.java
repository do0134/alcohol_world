package com.example.alchohol.user.model.entity;

import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "follow")
@Getter
public class FollowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private UserEntity follower;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private UserEntity following;

}
