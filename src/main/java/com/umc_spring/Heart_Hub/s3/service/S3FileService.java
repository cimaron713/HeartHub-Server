package com.umc_spring.Heart_Hub.s3.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class S3FileService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public List<String> upload(MultipartFile[] multipartFile, String parentCode) throws IOException {
        List<String> fileUrls = new ArrayList<>();

        for(MultipartFile mf : multipartFile) {
            String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("/yyyy-MM-dd HH:mm"));
            String s3FileName = parentCode + formatDate + mf.getOriginalFilename();

            ObjectMetadata objMeta = new ObjectMetadata();
            objMeta.setContentLength(mf.getInputStream().available());
            amazonS3.putObject(bucket, s3FileName, mf.getInputStream(), objMeta);

            fileUrls.add(amazonS3.getUrl(bucket, s3FileName).toString());
        }

        return fileUrls;
    }

    public void remove(String filePath) {
        try {
            amazonS3.deleteObject(bucket, filePath);
        } catch (AmazonServiceException e) {
            log.error("Failed to delete file from S3. bucket={}, path={}", bucket, filePath, e);
            throw new RuntimeException("Failed to delete file from S3.", e);
        } catch (SdkClientException e) {
            log.error("Failed to communicate with S3. bucket={}, path={}", bucket, filePath, e);
            throw new RuntimeException("Failed to communicate with S3.", e);
        }
    }

    public ResponseEntity<byte[]> download(String fileUrl) throws IOException {
        S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucket, fileUrl));
        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();
        byte[] bytes = IOUtils.toByteArray(objectInputStream);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(contentType(fileUrl));
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment", URLEncoder.encode(fileUrl, "UTF-8"));

        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }

    private MediaType contentType(String keyname) {
        String arr[] = keyname.split("\\.");
        String type = arr[arr.length - 1];
        switch (type) {
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
                return MediaType.IMAGE_JPEG;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}