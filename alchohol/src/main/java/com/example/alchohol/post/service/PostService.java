package com.example.alchohol.post.service;

import com.example.alchohol.common.error.AlcoholException;
import com.example.alchohol.common.error.ErrorCode;
import com.example.alchohol.post.controller.Response.PostResponse;
import com.example.alchohol.post.models.dto.Post;
import com.example.alchohol.post.models.entity.CommentEntity;
import com.example.alchohol.post.models.entity.PostEntity;
import com.example.alchohol.post.repository.PostRepository;
import com.example.alchohol.user.model.dto.User;
import com.example.alchohol.user.model.entity.UserEntity;
import com.example.alchohol.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createPost(String userEmail, String title, String content) {
        UserEntity userEntity = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new AlcoholException(ErrorCode.USER_NOT_FOUND));

        PostEntity postEntity = PostEntity.toEntity(title, content, userEntity);
        postRepository.save(postEntity);
    }

    public Post getPost(Long postId) {
        Post post = Post.fromEntity(postRepository.findById(postId).orElseThrow(() -> new AlcoholException(ErrorCode.POST_NOT_FOUND)));
        return post;
    }

}
