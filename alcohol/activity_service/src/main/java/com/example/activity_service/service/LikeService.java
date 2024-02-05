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

    private final ActivityService activityService;

    @Transactional
    public void postLike(Long userId, Long postId) {
        PostEntity post = postRepository.findById(postId).orElseThrow(() -> new AlcoholException(ErrorCode.POST_NOT_FOUND, "게시글이 존재하지 않습니다."));

        if (postLikeRepository.findByUserIdAndPost(userId, post).isPresent()){
            throw new AlcoholException(ErrorCode.ALREADY_LIKE, "이미 좋아요 누른 게시물입니다");
        }

        PostLikeEntity postLikeEntity = postLikeRepository.save(PostLikeEntity.toEntity(userId, post));
        activityService.savePostLikeActivity(postLikeEntity, userId);
    }

    @Transactional
    public void commentLike(Long userId, Long commentId) {

        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(() -> new AlcoholException(ErrorCode.POST_NOT_FOUND, "게시글이 존재하지 않습니다."));

        if (commentLikeRepository.findByUserIdAndComment(userId, comment).isPresent()){
            throw new AlcoholException(ErrorCode.ALREADY_LIKE, "이미 좋아요 눌렀습니다.");
        }

        CommentLikeEntity commentLikeEntity = commentLikeRepository.save(CommentLikeEntity.toEntity(userId, comment));
        activityService.saveCommentLikeActivity(commentLikeEntity, userId);
    }
}
