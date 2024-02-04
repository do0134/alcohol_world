package com.example.activity_service.service;

import com.example.activity_service.model.dto.Post;
import com.example.activity_service.model.dto.response.PostResponse;
import com.example.activity_service.model.entity.PostEntity;
import com.example.activity_service.repository.CommentRepository;
import com.example.activity_service.repository.FollowRepository;
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
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ActivityService activityService;


    @Transactional
    public void createPost(String userEmail, String title, String content) {
        UserEntity userEntity = userRepository.findByUserEmail(userEmail).orElseThrow(() -> new AlcoholException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다."));

        PostEntity postEntity = postRepository.save(PostEntity.toEntity(title, content, userEntity));
        activityService.savePostActivity(postEntity);
    }

    public PostResponse getPost(Long postId) {
        Post post = Post.fromEntity(postRepository.findById(postId).orElseThrow(() -> new AlcoholException(ErrorCode.POST_NOT_FOUND, "포스트가 없습니다.")));
        return PostResponse.fromPost(post);
    }

}
