package com.example.alchohol.user.service;

import com.example.alchohol.user.dto.User;
import com.example.alchohol.user.entity.UserEntity;
import com.example.alchohol.user.repository.UserRepository;
import com.example.alchohol.user.utils.JwtTokenProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;
    

    @Test
    void 성공적으로_회원가입_하는_경우() {
        String email= "useremail@naver.com";
        String password = "1234";
        String nickname = "test1";
        String statement = "hello world";
        String userImage = "test";

        when(userRepository.findByUserEmail(email)).thenReturn(Optional.empty());
        when(encoder.encode(password)).thenReturn("encrypt_password");

        Assertions.assertDoesNotThrow(() ->
                userService.signup(email, encoder.encode(password),nickname,statement,userImage)
        );
    }

    @Test
    void Jwt_생성_및_검증() {
        String email = "do0134@naver.com";
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = jwtTokenProvider.generateToken(email, secretKey, 1000L);


        Assertions.assertNotEquals(jwtTokenProvider.validate(token,email,secretKey), false);
    }
}
