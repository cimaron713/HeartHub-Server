package com.umc_spring.Heart_Hub.myPage.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class MyPageImgUploadDto {
    private MultipartFile myImgFile;

    public void setFiles(MultipartFile file) {
        if (file == null) {
            throw new IllegalArgumentException("files cannot be null");
        }

        this.myImgFile = file;
    }
}
