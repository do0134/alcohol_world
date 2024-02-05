package com.example.activity_service.service;

import com.example.activity_service.model.dto.Post;
import com.example.activity_service.model.dto.response.PostResponse;
import com.example.activity_service.model.entity.PostEntity;
import com.example.activity_service.repository.PostRepository;
import com.example.common.error.AlcoholException;
import com.example.common.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ActivityService activityService;


    @Transactional
    public void createPost(Long userId, String title, String content) {
        PostEntity postEntity = postRepository.save(PostEntity.toEntity(title, content, userId));
        activityService.savePostActivity(postEntity, userId);
    }

    public PostResponse getPost(Long postId) {
        Post post = Post.fromEntity(postRepository.findById(postId).orElseThrow(() -> new AlcoholException(ErrorCode.POST_NOT_FOUND, "포스트가 없습니다.")));
        return PostResponse.fromPost(post);
    }

}
