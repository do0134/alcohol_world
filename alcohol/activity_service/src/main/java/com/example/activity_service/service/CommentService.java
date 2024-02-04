package com.example.activity_service.service;

import com.example.activity_service.model.entity.CommentEntity;
import com.example.activity_service.model.entity.PostEntity;
import com.example.activity_service.repository.CommentRepository;
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
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ActivityService activityService;

    @Transactional
    public void createComment(String userEmail, Long postId, String content) {
        UserEntity user = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new AlcoholException(ErrorCode.USER_NOT_FOUND, "사용자가 존재하지 않습니다."));
        PostEntity post = postRepository.findById(postId).orElseThrow(() -> new AlcoholException(ErrorCode.POST_NOT_FOUND, "게시글이 존재하지 않습니다."));
        CommentEntity commentEntity = commentRepository.save(CommentEntity.toEntity(content,user,post));
        activityService.saveCommentActivity(commentEntity);
    }
}
