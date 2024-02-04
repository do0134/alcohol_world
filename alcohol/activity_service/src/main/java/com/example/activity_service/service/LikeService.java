package com.example.activity_service.service;

import com.example.activity_service.model.entity.CommentEntity;
import com.example.activity_service.model.entity.CommentLikeEntity;
import com.example.activity_service.model.entity.PostEntity;
import com.example.activity_service.model.entity.PostLikeEntity;
import com.example.activity_service.repository.CommentLikeRepository;
import com.example.activity_service.repository.CommentRepository;
import com.example.activity_service.repository.PostLikeRepository;
import com.example.activity_service.repository.PostRepository;
import com.example.common.error.AlcoholException;
import com.example.common.error.ErrorCode;
import com.example.user_service.model.entity.UserEntity;
import com.example.user_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final UserRepository userRepository;

    private final ActivityService activityService;

    @Transactional
    public void postLike(String userEmail, Long postId) {
        // TODO: 바로 userId를 받을 수 있는 지 확인해서 코드 줄이기
        UserEntity user =  userRepository.findByUserEmail(userEmail).orElseThrow(() -> new AlcoholException(ErrorCode.USER_NOT_FOUND, "사용자가 존재하지 않습니다."));
        PostEntity post = postRepository.findById(postId).orElseThrow(() -> new AlcoholException(ErrorCode.POST_NOT_FOUND, "게시글이 존재하지 않습니다."));

        if (postLikeRepository.findByUserAndPost(user, post).isPresent()){
            throw new AlcoholException(ErrorCode.ALREADY_LIKE, "이미 좋아요 누른 게시물입니다");
        }

        PostLikeEntity postLikeEntity = postLikeRepository.save(PostLikeEntity.toEntity(user, post));
        activityService.savePostLikeActivity(postLikeEntity);
    }

    @Transactional
    public void commentLike(String userEmail, Long commentId) {
        UserEntity user = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new AlcoholException(ErrorCode.USER_NOT_FOUND, "사용자가 존재하지 않습니다."));
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(() -> new AlcoholException(ErrorCode.POST_NOT_FOUND, "게시글이 존재하지 않습니다."));

        if (commentLikeRepository.findByUserAndComment(user, comment).isPresent()){
            throw new AlcoholException(ErrorCode.ALREADY_LIKE, "이미 좋아요 눌렀습니다.");
        }

        CommentLikeEntity commentLikeEntity = commentLikeRepository.save(CommentLikeEntity.toEntity(user, comment));
        activityService.saveCommentLikeActivity(commentLikeEntity);
    }
}
