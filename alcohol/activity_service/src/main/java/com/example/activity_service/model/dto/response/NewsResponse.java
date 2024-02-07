package com.example.activity_service.model.dto.response;

import com.example.activity_service.model.dto.News;
import lombok.Data;

import java.util.List;

@Data
public class NewsResponse {
    private List<News> news;
}
