package com.example.alchohol.user.controller;

import com.example.alchohol.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PostMapping("/signup")
    public void signup(@RequestBody Map<String, String> email) {
        String userEmail = email.get("email");
        log.info(String.format("%s get!!!!!!!!!!!!!!!!!!!!!", userEmail));
        userRepository.findByUserEmail(userEmail).ifPresent( it -> {
            log.error("222222222222222222222222222222222");
        });
    }


}
