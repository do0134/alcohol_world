package com.example.common.error;

import com.example.common.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(AlcoholException.class)
    public ResponseEntity<?> errorHandler(AlcoholException e) {
        log.error("Error occured {}", e.toString());

        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(Response.error(e.getErrorCode().name(),e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> applicationHandler(RuntimeException e) {
        log.error("Error occured {}", e.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.error(ErrorCode.INVALID_PERMISSION.name(), ErrorCode.INVALID_PERMISSION.getMessage()));
    }
}
