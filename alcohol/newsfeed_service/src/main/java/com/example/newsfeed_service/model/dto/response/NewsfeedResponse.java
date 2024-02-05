package com.example.newsfeed_service.model.dto.response;

import com.example.newsfeed_service.model.dto.Newsfeed;
import lombok.Data;

import java.util.List;

@Data
public class NewsfeedResponse {
    private List<Newsfeed> newsfeedList;
}
