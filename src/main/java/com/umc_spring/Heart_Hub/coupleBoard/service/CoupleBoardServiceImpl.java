package com.umc_spring.Heart_Hub.coupleBoard.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.umc_spring.Heart_Hub.constant.enums.ErrorCode;
import com.umc_spring.Heart_Hub.constant.exception.CustomException;
import com.umc_spring.Heart_Hub.coupleBoard.dto.CoupleBoardDto;
import com.umc_spring.Heart_Hub.coupleBoard.dto.CoupleBoardImageUploadDto;
import com.umc_spring.Heart_Hub.coupleBoard.model.CoupleBoard;
import com.umc_spring.Heart_Hub.coupleBoard.model.CoupleBoardImage;
import com.umc_spring.Heart_Hub.coupleBoard.repository.CoupleBoardRepository;
import com.umc_spring.Heart_Hub.coupleBoard.repository.ImageRepository;
import com.umc_spring.Heart_Hub.user.model.User;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CoupleBoardServiceImpl implements CoupleBoardService {

    private final UserRepository userRepository;
    private final CoupleBoardRepository coupleBoardRepository;
    private final ImageRepository imageRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    @Override
    public Long saveBoard(CoupleBoardDto.Request requestDto, CoupleBoardImageUploadDto boardImageUploadDto, String userName) {
        User user = userRepository.findByUsername(userName);
        if(user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        CoupleBoard result = CoupleBoard.builder()
                .content(requestDto.getContent())
                .user(user)
                .build();

        coupleBoardRepository.save(result);

        if(boardImageUploadDto.getFiles() != null && boardImageUploadDto.getFiles().length > 0) {
            try {
                List<String> fileUrls = upload(boardImageUploadDto.getFiles(), user.getUsername());

                for (String fileUrl : fileUrls) {
                    CoupleBoardImage img = CoupleBoardImage.builder()
                            .imgUrl(fileUrl)
                            .coupleBoard(result)
                            .build();

                    imageRepository.save(img);
                }
            } catch (IOException e) {
                throw new CustomException(ErrorCode.IMAGE_NOT_UPLOAD);
            }
        }

        return result.getPostId();
    }

    public List<String> upload(MultipartFile[] multipartFile, String username) throws IOException {
        List<String> fileUrls = new ArrayList<>();

        for(MultipartFile mf : multipartFile) {
            String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("/yyyy-MM-dd HH:mm"));
            String s3FileName = "CoupleCommunity/"+ username + formatDate + mf.getOriginalFilename();

            ObjectMetadata objMeta = new ObjectMetadata();
            objMeta.setContentLength(mf.getInputStream().available());
            amazonS3.putObject(bucket, s3FileName, mf.getInputStream(), objMeta);

            fileUrls.add(amazonS3.getUrl(bucket, s3FileName).toString());
        }

        return fileUrls;
    }

    @Override
    public CoupleBoardDto.Response detailBoard(Long postId) {
        CoupleBoard coupleBoard = coupleBoardRepository.findById(postId).orElseThrow(() -> {throw new CustomException(ErrorCode.POST_NOT_FOUND);});
        CoupleBoardDto.Response result = CoupleBoardDto.Response.builder()
                .board(coupleBoard)
                .build();

        return result;
    }

    @Override
    public List<CoupleBoardDto.Response> searchBoardList(LocalDate createAt, String username) {
        List<CoupleBoard> boardList;
        User user  = userRepository.findByUsername(username);
        if(user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        User mate = user.getUser();
        if(mate == null) {
            boardList = coupleBoardRepository.findAllByUserAndCreatedDate(user, createAt);
        } else {
            boardList = coupleBoardRepository.findAllByUserOrUserAndCreatedDate(user, mate, createAt);
        }

        List<CoupleBoardDto.Response> resultList = new ArrayList<>();

        for(CoupleBoard board : boardList) {
            resultList.add(new CoupleBoardDto.Response(board));
        }

        return resultList;
    }

    @Override
    public Long updateBoard(Long postId, CoupleBoardDto.Request requestDto) {
        CoupleBoard coupleBoard = coupleBoardRepository.findById(postId).orElseThrow(() -> {throw new CustomException(ErrorCode.POST_NOT_FOUND);});
        coupleBoard.update(requestDto.getContent());
        coupleBoardRepository.save(coupleBoard);

        return coupleBoard.getPostId();
    }

    @Override
    public void deleteBoard(Long postId) {
        CoupleBoard coupleBoard = coupleBoardRepository.findById(postId).orElseThrow(() -> {throw new CustomException(ErrorCode.POST_NOT_FOUND);});
        coupleBoardRepository.delete(coupleBoard);
    }

}
