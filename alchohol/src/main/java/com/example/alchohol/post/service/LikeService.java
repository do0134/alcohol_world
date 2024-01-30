package com.example.alchohol.post.service;

import com.example.alchohol.common.error.AlcoholException;
import com.example.alchohol.common.error.ErrorCode;
import com.example.alchohol.post.models.entity.PostEntity;
import com.example.alchohol.post.models.entity.PostLikeEntity;
import com.example.alchohol.post.repository.PostLikeRepository;
import com.example.alchohol.post.repository.PostRepository;
import com.example.alchohol.user.model.entity.UserEntity;
import com.example.alchohol.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public void postLike(String userEmail, Long postId) {
        UserEntity user = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new AlcoholException(ErrorCode.USER_NOT_FOUND, "사용자가 존재하지 않습니다."));
        PostEntity post = postRepository.findById(postId).orElseThrow(() -> new AlcoholException(ErrorCode.POST_NOT_FOUND, "게시글이 존재하지 않습니다."));

        if (postLikeRepository.findByUserAndPost(user, post).isPresent()){
            throw new AlcoholException(ErrorCode.ALREADY_LIKE, "이미 좋아요 누른 게시물입니다");
        }


        postLikeRepository.save(PostLikeEntity.toEntity(user, post));
    }
}
