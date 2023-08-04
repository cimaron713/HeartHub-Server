package com.umc_spring.Heart_Hub.myPage.service;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.umc_spring.Heart_Hub.constant.enums.CustomResponseStatus;
import com.umc_spring.Heart_Hub.constant.exception.CustomException;
import com.umc_spring.Heart_Hub.myPage.dto.MyPageDto;
import com.umc_spring.Heart_Hub.myPage.dto.MyPageImgUploadDto;
import com.umc_spring.Heart_Hub.user.model.User;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



@Service
@RequiredArgsConstructor
public class MyPageService {
    private final UserRepository userRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;

    /**
     * 유저프로필 페이지 프로필 조회
     */
    public MyPageDto.Response myPageDetail(String userName) {
        User user = userRepository.findByUsername(userName);
        MyPageDto.Response response = MyPageDto.Response
                .builder()
                .user(user)
                .build();
        return response;
    }

    ;

    /**
     * 유저프로필 페이지 수정
     */
    public MyPageDto.Response myPageProfileUpdate(String userName, MyPageDto.Request request, MyPageImgUploadDto myPageImgUploadDto) {
        User user = userRepository.findByUsername(userName);

        if (!request.getUserMessage().equals(user.getUserMessage())) {
            user.modifyUserMessage(request.getUserMessage());
        }
        if (!request.getUserNickName().equals(user.getNickname())) {
            user.modifyUserNickName(request.getUserNickName());
        }
        if (!myPageImgUploadDto.getMyImgFile().getOriginalFilename().equals(user.getUserImgUrl())) {

            if (myPageImgUploadDto.getMyImgFile() != null) {
                try {
                    String fileUrl = updateImg(myPageImgUploadDto.getMyImgFile(), user.getUsername());
                    user.modifyUserImgUrl(fileUrl);
                } catch (IOException e) {
                    throw new CustomException(CustomResponseStatus.IMAGE_NOT_UPLOAD);
                } catch (AmazonServiceException e) {
                    System.err.println(e.getErrorMessage());
                } catch (SdkClientException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        MyPageDto.Response result = MyPageDto.Response.builder()
                .user(user)
                .build();
        return result;
    }
    public String updateImg (MultipartFile mf, String username) throws IOException {
        String fileUrls = "";

        String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("/yyyy-MM-dd HH:mm"));
        String s3FileName = "MyPage/" + username + formatDate + mf.getOriginalFilename();

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(mf.getInputStream().available());
        amazonS3.putObject(bucket, s3FileName, mf.getInputStream(), objMeta);
        fileUrls = amazonS3.getUrl(bucket, s3FileName).toString();

        return fileUrls;
    }

    /**
     * 마이페이지 메뉴 상단 유저 프로필 조회
     */
    public MyPageDto.MyPage myProfileMenu (String userName){
        User user = userRepository.findByUsername(userName);
        MyPageDto.MyPage result = MyPageDto.MyPage.builder()
                .user(user)
                .build();
        return result;
    }
}


