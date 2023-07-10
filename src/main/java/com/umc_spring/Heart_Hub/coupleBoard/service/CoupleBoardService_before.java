package com.umc_spring.Heart_Hub.coupleBoard.service;

import com.umc_spring.Heart_Hub.coupleBoard.model.CoupleBoard;
import com.umc_spring.Heart_Hub.coupleBoard.repository.CoupleBoardRepository;
import com.umc_spring.Heart_Hub.coupleBoard.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Slf4j
public class CoupleBoardService_before {

    private final CoupleBoardRepository coupleBoardRepository;
    private final ImageRepository imageRepository;

    // 루트 경로 불러오기
    private final String rootPath = System.getProperty("user.dir");
    // 프로젝트 루트 경로에 있는 files 디렉토리
    private final String fileDir = rootPath + "/files/";

    @Transactional
    public Long createBoard(PostCreateRequestDto postCreateRequestDto,
                                   MultipartFile[] files) throws IOException {
        CoupleBoard coupleBoard = new CoupleBoard(postCreateRequestDto.getContent());
        Long postId = coupleBoardRepository.save(coupleBoard).getPostId();

        log.info("files = {}", files);
        if(!Objects.isNull(files)) {
            for(MultipartFile file : files) {
                String originalFilename = file.getOriginalFilename();
                log.info("originalFilename = {}", originalFilename);

                long imgSize = file.getSize();
                log.info("size = {}", imgSize);

                String contentType = file.getContentType();
                log.info("contentType = {}", contentType);

                String imgPath = fileDir + originalFilename;
                log.info("imgPath = {}", imgPath);

                ImageFile imageFiles = new ImageFile(originalFilename, imgPath, contentType, imgSize);
                imageFiles.setCoupleBoard(coupleBoard);
                imageRepository.save(imageFiles);

                file.transferTo(new File(imgPath));
            }
        }
        return postId;
    }
}
