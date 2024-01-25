package com.example.alchohol.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response <T> {
    private String resultCode;
    private T result;

    public static <T> Response<T> success(T result) {
        return new Response<>("SUCCESS", result);
    }

    public static Response<Void> success() {
        return new Response<Void>("SUCCESS", null);
    }

    public static Response<Void> error(String resultCode) {
        return new Response<>(resultCode, null);
    }

    public String toStream() {
        if (result == null) {
            return "{" + "\"resultCode\":" + "\""+ resultCode + "\"" +
                    "\"result\":" + null + "}";
        }

        return "{" + "\"resultCode\":" + "\""+ resultCode + "\"" +
                "\"result\":" + result + "}";
    }
}