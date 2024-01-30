package com.example.alchohol.post.service;

import com.example.alchohol.common.error.AlcoholException;
import com.example.alchohol.common.error.ErrorCode;
import com.example.alchohol.post.controller.Response.PostResponse;
import com.example.alchohol.post.models.dto.Activate;
import com.example.alchohol.post.models.dto.Post;
import com.example.alchohol.post.models.entity.CommentEntity;
import com.example.alchohol.post.models.entity.PostEntity;
import com.example.alchohol.post.models.entity.PostLikeEntity;
import com.example.alchohol.post.repository.CommentRepository;
import com.example.alchohol.post.repository.PostLikeRepository;
import com.example.alchohol.post.repository.PostRepository;
import com.example.alchohol.user.model.entity.FollowEntity;
import com.example.alchohol.user.model.entity.UserEntity;
import com.example.alchohol.user.repository.FollowRepository;
import com.example.alchohol.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;


    @Transactional
    public void createPost(String userEmail, String title, String content) {
        UserEntity userEntity = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new AlcoholException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다."));

        PostEntity postEntity = PostEntity.toEntity(title, content, userEntity);
        postRepository.save(postEntity);
    }

    public PostResponse getPost(Long postId) {
        Post post = Post.fromEntity(postRepository.findById(postId).orElseThrow(() -> new AlcoholException(ErrorCode.POST_NOT_FOUND, "포스트가 없습니다.")));
        return PostResponse.fromPost(post);
    }

    public List<Activate> getNewsFeed(String userEmail) {
        UserEntity user = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new AlcoholException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다."));
        List<FollowEntity> followingList = followRepository.findAllByFollower(user).orElse(null);


        if (followingList == null) {
            return new ArrayList<>();
        }

        List<Long> followingIdList = new ArrayList<>();

        for (FollowEntity followEntity: followingList) {
            followingIdList.add(followEntity.getFollowing().getId());
        }

        List<Activate> feeds = makeFeeds(followingIdList, followingList);
        return feeds;
    }

    public List<Activate> getReverseFeed(String userEmail) {
        UserEntity user = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new AlcoholException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다."));
        List<FollowEntity> followerList = followRepository.findAllByFollowing(user).orElse(null);

        List<Long> followerIdList = new ArrayList<>();

        if (followerList == null) {
            return new ArrayList<>();
        }

        for (FollowEntity followEntity: followerList) {
            followerIdList.add(followEntity.getFollower().getId());
        }

        List<Activate> feeds = makeFeeds(followerIdList, followerList);
        return feeds;
    }

    public List<Activate> makeFeeds(List<Long> followIds, List<FollowEntity> followList) {
        List<Activate> feeds = new ArrayList<>();
        List<PostEntity> postEntityList = postRepository.findAllByUserInFollowingIds(followIds);
        List<PostLikeEntity> postLikeEntityList = postLikeRepository.findAllByUserInFollowingIds(followIds);
        List<CommentEntity> commentEntityList = commentRepository.findAllByUserInFollowingIds(followIds);
        List<FollowEntity> followEntityList = followRepository.findAllByUserInFollowingIds(followIds);

        if (!postEntityList .isEmpty()) {
            for (PostEntity post:postEntityList) {
                feeds.add(Activate.fromEntity(post));
            }
        }

        if (!postLikeEntityList.isEmpty()) {
            for (PostLikeEntity postLike: postLikeEntityList) {
                feeds.add(Activate.fromEntity(postLike));
            }
        }

        if (!commentEntityList.isEmpty()) {
            for (CommentEntity comment: commentEntityList) {
                feeds.add(Activate.fromEntity(comment));
            }
        }

        if (!followEntityList.isEmpty()) {
            for (FollowEntity followEntity: followList) {
                feeds.add(Activate.fromEntity(followEntity));
            }
        }

        Comparator<Activate> sortKey = Comparator.comparing(Activate::getCreatedAt).reversed();
        feeds.sort(sortKey);

        return feeds;
    }
}
