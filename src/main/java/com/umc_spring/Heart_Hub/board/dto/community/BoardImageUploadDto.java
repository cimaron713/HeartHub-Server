package com.umc_spring.Heart_Hub.board.dto.community;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class BoardImageUploadDto {
    private List<MultipartFile> communityFiles;
}
