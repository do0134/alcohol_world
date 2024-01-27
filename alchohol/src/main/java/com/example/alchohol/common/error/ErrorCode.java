package com.example.alchohol.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다."),
    DUPLICATED_NICKNAME(HttpStatus.CONFLICT, "중복된 닉네임 입니다."),
    INVALID_EMAIL_OR_PASSWORD(HttpStatus.UNAUTHORIZED, "이메일이나 비밀번호가 잘못됐습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    CANT_SAVE(HttpStatus.BAD_REQUEST, "프로필 사진을 저장할 수 없습니다."),
    DUPLICATED_PROFILE_IMAGE(HttpStatus.CONFLICT, "이미 같은 닉네임으로 저장된 프로필 사진이 존재합니다."),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "허가받지 않은 접근입니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    ALREADY_LIKE(HttpStatus.BAD_REQUEST, "이미 좋아요를 눌렀습니다.");

    private final HttpStatus status;
    private final String message;
}
