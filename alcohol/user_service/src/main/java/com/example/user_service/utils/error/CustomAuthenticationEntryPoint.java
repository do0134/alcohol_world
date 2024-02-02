package com.example.user_service.utils.error;

import com.example.common.error.ErrorCode;
import com.example.common.response.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(ErrorCode.INVALID_PERMISSION.getStatus().value());
        response.getWriter().write(Response.error(ErrorCode.INVALID_PERMISSION.name(), ErrorCode.INVALID_PERMISSION.getMessage()).toStream());
    }
}
