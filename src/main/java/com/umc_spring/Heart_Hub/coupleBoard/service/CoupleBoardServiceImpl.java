package com.umc_spring.Heart_Hub.coupleBoard.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.umc_spring.Heart_Hub.constant.enums.CustomResponseStatus;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CoupleBoardServiceImpl implements CoupleBoardService {

    private final UserRepository userRepository;
    private final CoupleBoardRepository coupleBoardRepository;
    private final ImageRepository imageRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    @Override
    public Long saveBoard(CoupleBoardDto.Request requestDto, CoupleBoardImageUploadDto boardImageUploadDto, String userName) throws IOException {
        User user = userRepository.findByUsername(userName);
        if(user == null) {
            throw new CustomException(CustomResponseStatus.USER_NOT_FOUND);
        }

        CoupleBoard result = CoupleBoard.builder()
                .content(requestDto.getContent())
                .status("Y")
                .user(user)
                .build();

        coupleBoardRepository.save(result);
        log.info("File save successfully to the bucket {}", bucket);

        if(boardImageUploadDto.getFiles() != null) {
            try {
                List<String> fileUrls = upload(boardImageUploadDto.getFiles(), user.getUsername());

                for (String fileUrl : fileUrls) {
                    CoupleBoardImage img = CoupleBoardImage.builder()
                            .imgUrl(fileUrl)
                            .coupleBoard(result)
                            .build();

                    imageRepository.save(img);
                }
                log.info("File upload successfully to the bucket {}", bucket);
            } catch (IOException e) {
                throw new CustomException(CustomResponseStatus.IMAGE_NOT_UPLOAD);
            } catch (AmazonServiceException e) {
                System.err.println(e.getErrorMessage());
            } catch (SdkClientException e) {
                System.err.println(e.getMessage());
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
        CoupleBoard coupleBoard = coupleBoardRepository.findById(postId).orElseThrow(() -> {throw new CustomException(CustomResponseStatus.POST_NOT_FOUND);});
        CoupleBoardDto.Response result = CoupleBoardDto.Response.builder()
                .board(coupleBoard)
                .build();

        return result;
    }

    @Override
    public List<CoupleBoardDto.Response> searchBoardList(LocalDate createAt, String username) {
        List<CoupleBoard> boardList = new ArrayList<>();
        User user  = userRepository.findByUsername(username);
        if(user == null) {
            throw new CustomException(CustomResponseStatus.USER_NOT_FOUND);
        }

        boardList.addAll(coupleBoardRepository.findAllByUserAndCreatedDate(user, createAt));

        User mate = user.getUser();
        if(mate != null) {
            boardList.addAll(coupleBoardRepository.findAllByUserAndCreatedDate(user, createAt));
        }

        List<CoupleBoardDto.Response> resultList = new ArrayList<>();

        for(CoupleBoard board : boardList) {
            resultList.add(new CoupleBoardDto.Response(board));
        }

        return resultList;
    }

    @Override
    public Long updateBoard(Long postId, CoupleBoardDto.Request requestDto) {
        CoupleBoard coupleBoard = coupleBoardRepository.findById(postId).orElseThrow(() -> {throw new CustomException(CustomResponseStatus.POST_NOT_FOUND);});
        coupleBoard.update(requestDto.getContent());
        coupleBoardRepository.save(coupleBoard);

        return coupleBoard.getPostId();
    }

    @Override
    public void deleteBoard(Long postId) {
        CoupleBoard coupleBoard = coupleBoardRepository.findById(postId).orElseThrow(() -> {throw new CustomException(CustomResponseStatus.POST_NOT_FOUND);});
        coupleBoardRepository.delete(coupleBoard);
    }

}
