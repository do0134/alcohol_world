package com.example.user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(
        scanBasePackages = {"com.example.common", "com.example.user_service"}
)
public class UserServiceApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(UserServiceApplication.class, args);
    }

}
