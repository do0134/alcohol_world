package com.example.activity_service.service;

import com.example.activity_service.model.ActivityUserClient;
import com.example.activity_service.model.dto.NewsFeed;
import com.example.activity_service.model.dto.PostUserDto;
import com.example.activity_service.model.entity.PostEntity;
import com.example.activity_service.repository.FollowRepository;
import com.example.activity_service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsFeedService {
    private final PostRepository postRepository;
    private final FollowRepository followRepository;
    private final ActivityUserClient activityUserClient;

    public List<NewsFeed> newsFeed(Long userId) {
        List<Long> followEntityList = findFollowing(userId);
        List<NewsFeed> newsFeeds = new ArrayList<>();

        if (followEntityList.size() == 0) {
            return  newsFeeds;
        }

        Optional<List<PostEntity>> postEntityList = postRepository.findAllByUserInFollowingIds(followEntityList);

        if (postEntityList.isEmpty()) {
            return newsFeeds;
        }

        for (PostEntity postEntity: postEntityList.get()) {
            PostUserDto postUser = activityUserClient.getUser(postEntity.getUserId()).getResult();
            NewsFeed newsFeed = NewsFeed.fromEntity(postEntity, postUser);
            newsFeeds.add(newsFeed);
        }

        Comparator<NewsFeed> sortKey = Comparator.comparing(NewsFeed::getCreatedAt).reversed();
        newsFeeds.sort(sortKey);

        return newsFeeds;
    }

    public List<Long> findFollowing(Long userId) {
        Optional<List<Long>> followEntityList = followRepository.findAllFollowingByFollower(userId);

        return followEntityList.orElseGet(ArrayList::new);

    }
}
