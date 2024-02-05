package com.example.activity_service.service;

import com.example.activity_service.model.ActivityUserClient;
import com.example.activity_service.model.dto.Comment;
import com.example.activity_service.model.dto.Post;
import com.example.activity_service.model.dto.PostUserDto;
import com.example.activity_service.model.dto.response.PostResponse;
import com.example.activity_service.model.entity.CommentEntity;
import com.example.activity_service.model.entity.PostEntity;
import com.example.activity_service.repository.PostRepository;
import com.example.common.error.AlcoholException;
import com.example.common.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ActivityService activityService;
    private final ActivityUserClient activityUserClient;


    @Transactional
    public void createPost(Long userId, String title, String content) {
        PostEntity postEntity = postRepository.save(PostEntity.toEntity(title, content, userId));
        activityService.savePostActivity(postEntity, userId);
    }

    public PostResponse getPost(Long postId) {
        PostEntity postEntity = postRepository.findById(postId). orElseThrow(() -> new AlcoholException(ErrorCode.POST_NOT_FOUND, "포스트가 없습니다."));
        PostUserDto postUser = activityService.getUser(postEntity.getUserId());
        Post post = Post.fromEntityAndFeign(postEntity, postUser);
        List<Comment> commentList = new ArrayList<>();

        for (CommentEntity comment: postEntity.getCommentList()) {
            PostUserDto postUserDto = activityUserClient.getUser(comment.getUserId()).getResult();
            Comment commentDto = Comment.fromEntityAndFeign(comment, postUserDto);
            commentList.add(commentDto);
        }


        return PostResponse.fromPost(post, commentList, (long)postEntity.getPostLikeList().size());
    }

}
