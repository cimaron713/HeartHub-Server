package com.umc_spring.Heart_Hub.coupleBoard.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class CoupleBoardImageUploadDto {
    private MultipartFile[] files;
}
