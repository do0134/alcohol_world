package com.example.alchohol.user.utils.filter;

import com.example.alchohol.common.error.AlcoholException;
import com.example.alchohol.common.error.ErrorCode;
import com.example.alchohol.user.model.dto.User;
import com.example.alchohol.user.service.UserService;
import com.example.alchohol.user.utils.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.secret-key}")
    private final String secretKey;

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        try {
            final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
            final Optional<String> token = Optional.ofNullable(resolveToken(header));
            if (token.isEmpty()) {
                log.error("헤더가 Bearer 로 시작하지 않습니다. {}", request.getRequestURI());
                throw new AlcoholException(ErrorCode.INVALID_PERMISSION, "인증받지 않은 요청입니다.");
            } else {
                Authentication auth = getAuthentication(token.get(), secretKey);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (RuntimeException e) {
            log.error(e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String resolveToken(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }
        return header.split(" ")[1].trim();
    }

    private Authentication getAuthentication(String token, String key) {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String userEmail = jwtTokenProvider.getUserEmail(token, key);

        if (!jwtTokenProvider.validate(token, userEmail, key)) {
            return null;
        }

        User user = userService.loadUserByUserEmail(userEmail);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user,null,user.getAuthorities()
        );

        return authentication;
    }
}
