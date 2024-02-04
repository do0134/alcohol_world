package com.example.activity_service.service;

import com.example.activity_service.model.entity.FollowEntity;
import com.example.activity_service.repository.FollowRepository;
import com.example.common.error.AlcoholException;
import com.example.common.error.ErrorCode;
import com.example.user_service.model.entity.UserEntity;
import com.example.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final ActivityService activityService;

    @Transactional
    public void userFollow(String userEmail, Long followingId) {
        UserEntity follower = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new AlcoholException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다."));

        if (follower.getId().equals(followingId)) {
            throw new AlcoholException(ErrorCode.ALREADY_FOLLOW, "스스로를 팔로우할 수 없습니다.");
        }

        UserEntity following = userRepository.findById(followingId).orElseThrow(() -> new AlcoholException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다."));

        Optional<FollowEntity> ifFollow = followRepository.findByFollowerAndFollowing(follower, following);

        if (ifFollow.isPresent()) {
            throw new AlcoholException(ErrorCode.ALREADY_FOLLOW, String.format("%s를 이미 팔로우 했습니다.", following.getNickname()));
        }

        FollowEntity followEntity = followRepository.save(FollowEntity.toEntity(follower, following));
        activityService.saveFollowActivity(followEntity);
    }
}
