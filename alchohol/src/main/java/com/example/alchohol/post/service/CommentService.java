package com.example.alchohol.post.service;

import com.example.alchohol.common.error.AlcoholException;
import com.example.alchohol.common.error.ErrorCode;
import com.example.alchohol.post.models.entity.CommentEntity;
import com.example.alchohol.post.models.entity.PostEntity;
import com.example.alchohol.post.repository.CommentRepository;
import com.example.alchohol.post.repository.PostRepository;

import com.example.alchohol.user.model.entity.UserEntity;
import com.example.alchohol.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public void createComment(String userEmail, Long postId, String content) {
        UserEntity user = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new AlcoholException(ErrorCode.USER_NOT_FOUND, "사용자가 존재하지 않습니다."));
        PostEntity post = postRepository.findById(postId).orElseThrow(() -> new AlcoholException(ErrorCode.POST_NOT_FOUND, "게시글이 존재하지 않습니다."));
        commentRepository.save(CommentEntity.toEntity(content,user,post));
    }

}
