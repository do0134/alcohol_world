package com.example.alchohol.user.service;

import com.example.alchohol.user.dto.User;
import com.example.alchohol.user.entity.UserEntity;
import com.example.alchohol.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder encoder;


    @Transactional
    public User signup(String userEmail, String password, String nickname, String statement, String userImage) {
        userRepository.findByUserEmail(userEmail).ifPresent(it ->{
            throw new IllegalArgumentException(String.format("%s는 존재하는 email입니다.",userEmail));
        });

        UserEntity userEntity = UserEntity.toEntity(
                userEmail, password, nickname,statement,userImage
        );
        userRepository.save(userEntity);

        return User.fromEntity(userEntity);
    }
}
