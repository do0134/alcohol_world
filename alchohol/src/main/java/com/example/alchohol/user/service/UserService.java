package com.example.alchohol.user.service;

import com.example.alchohol.common.error.AlcoholException;
import com.example.alchohol.common.error.ErrorCode;
import com.example.alchohol.user.model.dto.User;
import com.example.alchohol.user.model.entity.UserEntity;
import com.example.alchohol.user.repository.UserRepository;
import com.example.alchohol.user.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final FileService fileService;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token-expired-time-ms}")
    private Long expiredTimeMs;




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
    public User signup(String userEmail, String password, String nickname, String statement, MultipartFile userImage) {
        userRepository.findByUserEmail(userEmail).ifPresent(it ->{
            throw new AlcoholException(ErrorCode.DUPLICATED_EMAIL, "이미 가입한 회원입니다.");
        });

        checkNickname(nickname);
        String imagePath = fileService.saveImage(userImage, nickname);

        UserEntity userEntity = UserEntity.toEntity(
                userEmail, encoder.encode(password), nickname,statement,imagePath
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

    public String userLogin(String userEmail, String password) {
        if (!checkPassword(userEmail, password)) {
            throw new AlcoholException(ErrorCode.INVALID_EMAIL_OR_PASSWORD, "이메일이나 비밀번호가 잘못됐습니다.");
        }

        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        return jwtTokenProvider.generateToken(userEmail, secretKey, expiredTimeMs);
    }

    public User userProfile(Long userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);

        if (userEntity.isEmpty()) {
            throw new AlcoholException(ErrorCode.USER_NOT_FOUND);
        }

        return User.fromEntity(userEntity.get());
    }

    @Transactional
    public User updateUserProfile(Long userId, String userEmail, String nickname, String statement, Optional<MultipartFile> image) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new AlcoholException(ErrorCode.USER_NOT_FOUND));

        checkUserSelf(userEntity.getUserEmail(), userEmail);

        image.ifPresent(multipartFile -> fileService.updateImage(userEntity.getUserImage(), multipartFile, userEntity.getNickname()));

        userEntity.setNickname(nickname);
        userEntity.setStatement(statement);
        userEntity.setUserImage(userEntity.getUserImage());

        return User.fromEntity(userRepository.saveAndFlush(userEntity));
    }

    @Transactional
    public void updatePassword(Long userId,String userEmail, String password) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new AlcoholException(ErrorCode.INVALID_PERMISSION, "본인의 비밀번호만 수정가능합니다."));

        checkUserSelf(userEntity.getUserEmail(), userEmail);

        userEntity.setPassword(encoder.encode(password));

        userRepository.saveAndFlush(userEntity);
    }

    public Boolean checkPassword(String userEmail, String password) {
        Optional<UserEntity> user = userRepository.findByUserEmail(userEmail);

        return user.filter(userEntity -> encoder.matches(password, userEntity.getPassword())).isPresent();
    }

    public User loadUserByUserEmail(String userEmail) {
        Optional<UserEntity> userEntity = userRepository.findByUserEmail(userEmail);
        if (userEntity.isEmpty()) {
            throw new AlcoholException(ErrorCode.USER_NOT_FOUND,"사용자를 찾을 수 없습니다.");
        }

        User user = User.fromEntity(userEntity.get());
        return user;
    }

    public void checkUserSelf(String userEmail1, String userEmail2) {
        if (!userEmail1.equals(userEmail2)) {
            throw new AlcoholException(ErrorCode.INVALID_PERMISSION, "본인의 프로필만 수정 가능합니다.");
        }
    }

}
