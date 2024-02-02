package com.example.user_service.service;

import com.example.common.error.AlcoholException;
import com.example.common.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
@Slf4j
public class FileService {

    @Value("${image.upload-dir}")
    private String uploadDir;

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
                throw new AlcoholException(ErrorCode.CANNOT_SAVE);
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

    public void updateImage(String filePath, MultipartFile image, String nickname) {
        File oldFile = new File(filePath);

        if (oldFile.delete()) {
            log.info("기존 파일을 삭제했습니다.");
        } else {
            log.info("파일 삭제에 실패했습니다.");
        }

        saveImage(image, nickname);
    }
}