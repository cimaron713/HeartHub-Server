package com.umc_spring.Heart_Hub.board.dto.community;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class BoardImageUploadDto {
    private MultipartFile[] communityFiles;

    public void setFiles(MultipartFile[] files) {
        if (files == null) {
            throw new IllegalArgumentException("files cannot be null");
        }
        for (MultipartFile file : files) {
            if (file == null) {
                throw new IllegalArgumentException("file in files cannot be null");
            }
        }
        this.communityFiles = files;
    }
}
