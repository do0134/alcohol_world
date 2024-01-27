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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token-expired-time-ms}")
    private Long expiredTimeMs;

    @Value("${image.upload-dir}")
    private String uploadDir;


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

        String imagePath = saveImage(userImage, nickname);

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

    public String saveImage(MultipartFile image, String fileName) {
        Path path = Paths.get(uploadDir);
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            String fileExtension = getFileExtension(Objects.requireNonNull(image.getOriginalFilename()));
            String newFileName = uploadDir + "/" + fileName+fileExtension;

            if (Files.exists(Paths.get(newFileName))) {
                throw new AlcoholException(ErrorCode.DUPLICATED_PROFILE_IMAGE);
            }

            File file = new File(newFileName);
            if (!createFile(file)) {
                throw new AlcoholException(ErrorCode.CANT_SAVE);
            }

            writeFile(file, image);

            return newFileName;

        } catch (IOException | NullPointerException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public Boolean createFile(File file) {
        try {
            return file.createNewFile();
        } catch (IOException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public void writeFile(File file, MultipartFile image) {
        try (FileOutputStream fos = new FileOutputStream(file)){
            byte[] bytes = image.getBytes();
            fos.write(bytes);
        } catch (IOException e) {
            log.error(e.getMessage());
        }



    }
}
