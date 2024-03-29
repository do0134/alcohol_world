package com.example.activity_service.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequest {
    private String content;

    public CommentRequest(String content) {
        this.content = content;
    }
}
