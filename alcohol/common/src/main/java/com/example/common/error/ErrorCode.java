package com.example.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일입니다."),
    DUPLICATED_NICKNAME(HttpStatus.BAD_REQUEST, "중복된 닉네임 입니다."),
    INVALID_EMAIL_OR_PASSWORD(HttpStatus.BAD_REQUEST, "이메일이나 비밀번호가 잘못됐습니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다."),
    CANNOT_SAVE(HttpStatus.BAD_REQUEST, "프로필 사진을 저장할 수 없습니다."),
    DUPLICATED_PROFILE_IMAGE(HttpStatus.BAD_REQUEST, "이미 같은 닉네임으로 저장된 프로필 사진이 존재합니다."),
    INVALID_PERMISSION(HttpStatus.BAD_REQUEST, "Invalid Permission"),

    ALREADY_FOLLOW(HttpStatus.BAD_REQUEST, "이미 팔로우한 계정입니다."),
    CANNOT_FOLLOW_YOURSELF(HttpStatus.BAD_REQUEST, "자신을 팔로우할 수 없습니다."),

    POST_NOT_FOUND(HttpStatus.BAD_REQUEST, "게시글을 찾을 수 없습니다."),
    ALREADY_LIKE(HttpStatus.BAD_REQUEST, "이미 좋아요를 눌렀습니다."),

    NO_SUCH_ITEM(HttpStatus.BAD_REQUEST, "No Such Items."),
    ITEM_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "Item Already Exist"),

    NO_SUCH_ORDER(HttpStatus.BAD_REQUEST, "No such Order"),
    INVALID_TIME(HttpStatus.BAD_REQUEST, "Cannot access item now");

    private final HttpStatus status;
    private final String message;
}
