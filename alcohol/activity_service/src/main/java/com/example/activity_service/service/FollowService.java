package com.example.activity_service.service;

import com.example.activity_service.model.entity.FollowEntity;
import com.example.activity_service.repository.FollowRepository;
import com.example.common.error.AlcoholException;
import com.example.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final ActivityService activityService;

    @Transactional
    public void userFollow(Long followerId, Long followingId) {
        if (followerId.equals(followingId)) {
            throw new AlcoholException(ErrorCode.ALREADY_FOLLOW, "스스로를 팔로우할 수 없습니다.");
        }


        Optional<FollowEntity> ifFollow = followRepository.findByFollowerAndFollowing(followerId, followingId);

        if (ifFollow.isPresent()) {
            throw new AlcoholException(ErrorCode.ALREADY_FOLLOW, String.format("%s를 이미 팔로우 했습니다.", followingId));
        }

        FollowEntity followEntity = followRepository.save(FollowEntity.toEntity(followerId, followingId));
        activityService.saveFollowActivity(followEntity);
    }
}
