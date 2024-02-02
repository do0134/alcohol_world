package com.example.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlcoholException extends RuntimeException{
    private ErrorCode errorCode;
    private String message;

    public AlcoholException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage(){
        if (message == null) {
            return errorCode.getMessage();
        }

        return String.format("%s %s", errorCode.getMessage(),message);
    }
}
