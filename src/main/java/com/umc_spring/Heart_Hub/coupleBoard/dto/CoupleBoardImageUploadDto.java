package com.umc_spring.Heart_Hub.coupleBoard.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//다중 이미지를 전달받을 DTO
@Getter
public class CoupleBoardImageUploadDto {
    private List<MultipartFile> files;
}
