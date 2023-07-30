package com.umc_spring.Heart_Hub.s3.controller;

import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import com.umc_spring.Heart_Hub.s3.service.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/s3")
public class S3FileController {

    private final S3FileService s3Upload;

    @PostMapping(value = "/upload", consumes = "multipart/*")
    public ResponseEntity<ApiResponse<List<String>>> uploadFile(String parentCode, @RequestPart("images") MultipartFile[] multipartFiles) throws IOException {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(s3Upload.upload(multipartFiles, parentCode), "File Upload Success!"));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<ApiResponse<String>> deleteFile(String filePath) throws IOException {
        s3Upload.remove(filePath);
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent("File Delete Success!"));
    }

    @GetMapping(value = "/download")
    public ResponseEntity<byte[]> downloadFile(String fileUrl) throws IOException {
        String filePath = fileUrl.substring(56);
        return s3Upload.download(filePath);
    }


}
