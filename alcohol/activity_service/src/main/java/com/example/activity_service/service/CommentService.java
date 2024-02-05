package com.example.activity_service.service;

import com.example.activity_service.model.entity.CommentEntity;
import com.example.activity_service.model.entity.PostEntity;
import com.example.activity_service.repository.CommentRepository;
import com.example.activity_service.repository.PostRepository;
import com.example.common.error.AlcoholException;
import com.example.common.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ActivityService activityService;

    @Transactional
    public void createComment(Long userId, Long postId, String content) {
        PostEntity post = postRepository.findById(postId).orElseThrow(() -> new AlcoholException(ErrorCode.POST_NOT_FOUND, "게시글이 존재하지 않습니다."));
        CommentEntity commentEntity = commentRepository.save(CommentEntity.toEntity(content,userId,post));
        activityService.saveCommentActivity(commentEntity, userId);
    }

//    public List<Comment> getComments(Long userId, Long postId) {
//        PostEntity post = postRepository.findById(postId).orElseThrow(() -> new AlcoholException(ErrorCode.POST_NOT_FOUND, "게시글이 존재하지 않습니다."));
//
//    }
}
