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
            throw new AlcoholException(ErrorCode.ALREADY_FOLLOW);
        }


        Optional<FollowEntity> ifFollow = followRepository.findByFollowerAndFollowing(followerId, followingId);

        if (ifFollow.isPresent()) {
            throw new AlcoholException(ErrorCode.ALREADY_FOLLOW);
        }

        FollowEntity followEntity = followRepository.save(FollowEntity.toEntity(followerId, followingId));
        activityService.saveFollowActivity(followEntity);
    }
}
