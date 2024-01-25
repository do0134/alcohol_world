package com.example.alchohol.user.service;

import com.example.alchohol.common.error.AlcoholException;
import com.example.alchohol.common.error.ErrorCode;
import com.example.alchohol.user.dto.User;
import com.example.alchohol.user.entity.UserEntity;
import com.example.alchohol.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    /**
     * 회원가입 API
     * Email 중복체크를 한 다음, 회원가입 시킨다.
     * Email이 중복되었다면 alcohol exception(Duplicated_email)
     * @param userEmail
     * @param password
     * @param nickname
     * @param statement
     * @param userImage
     * @return User
     */
    @Transactional
    public User signup(String userEmail, String password, String nickname, String statement, String userImage) {
        userRepository.findByUserEmail(userEmail).ifPresent(it ->{
            throw new AlcoholException(ErrorCode.DUPLICATED_EMAIL, "이미 가입한 회원입니다.");
        });

        UserEntity userEntity = UserEntity.toEntity(
                userEmail, encoder.encode(password), nickname,statement,userImage
        );

        userRepository.save(userEntity);

        return User.fromEntity(userEntity);
    }

    public Boolean checkNickname(String nickname) {
        userRepository.findByNickname(nickname).ifPresent(it -> {
            throw new AlcoholException(ErrorCode.DUPLICATED_NICKNAME, "이미 존재하는 닉네임입니다.");
        });

        return true;
    }
}
