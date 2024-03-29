package com.example.activity_service.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostRequest {
    private String title;
    private String content;

    public PostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
