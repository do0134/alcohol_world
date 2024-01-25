package user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Lob
    @Column(name = "statement")
    private String statement;

    @Column(name = "user_image")
    private String userImage;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;


    @OneToMany(mappedBy = "follower")
    private List<FollowEntity> followerList;

    @OneToMany(mappedBy = "following")
    private List<FollowEntity> followingList;

    @OneToMany(mappedBy = "device_name")
    private List<DeviceEntity> deviceList;

    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }
}
