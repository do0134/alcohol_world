package com.example.alchohol.post.models.dto;

import com.example.alchohol.post.models.entity.CommentEntity;
import com.example.alchohol.post.models.entity.PostEntity;
import com.example.alchohol.post.models.entity.PostLikeEntity;
import com.example.alchohol.user.model.entity.FollowEntity;
import lombok.*;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Data
public class Activate {
    private ActiveType type;
    private String content;
    private FeedPost post;
    private Timestamp createdAt;

    public static Activate fromEntity(PostEntity post) {
        return new Activate(
                ActiveType.POST,
                "",
                FeedPost.fromEntity(post),
                post.getCreatedAt()
        );
    }

    public static Activate fromEntity(PostLikeEntity postLike) {
        return new Activate(
                ActiveType.LIKE,
                String.format("%s가 %s를 좋아합니다.",postLike.getUser().getNickname(), postLike.getPost().getTitle()),
                null,
                postLike.getCreatedAt()
        );
    }

    public static Activate fromEntity(CommentEntity comment) {
        return new Activate(
                ActiveType.COMMENT,
                String.format("%s가 %s를 팔로우하기 시작했습니다.",comment.getUser().getNickname(),comment.getPost().getTitle()),
                null,
                comment.getCreatedAt()
        );
    }

    public static Activate fromEntity(FollowEntity follow) {
        return new Activate(
                ActiveType.COMMENT,
                String.format("%s가 %s에 댓글을 달았습니다.",follow.getFollower().getNickname(),follow.getFollowing().getNickname()),
                null,
                follow.getCreatedAt()
        );
    }
}
