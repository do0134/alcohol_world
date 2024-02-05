package com.example.newsfeed_service.config;

import com.example.common.error.AlcoholException;
import com.example.common.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(AlcoholException.class)
    public Response<?> errorHandler(AlcoholException e) {
        log.error("Error occured {}", e.toString());
        return Response.error(e.getErrorCode().toString(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public Response<?> applicationHandler(RuntimeException e) {
        log.error("Error occured {}", e.toString());
        return Response.error(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage());
    }
}