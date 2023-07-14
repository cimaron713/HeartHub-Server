package com.umc_spring.Heart_Hub.coupleBoard.service;

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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CoupleBoardServiceImpl implements CoupleBoardService {

    private final UserRepository userRepository;
    private final CoupleBoardRepository coupleBoardRepository;
    private final ImageRepository imageRepository;

    @Value("${file.boardImgPath}")
    private String uploadFolder;

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

        if(boardImageUploadDto.getFiles() != null && !boardImageUploadDto.getFiles().isEmpty()) {
            for (MultipartFile file : boardImageUploadDto.getFiles()) {
                UUID uuid = UUID.randomUUID();
                String imgFileName = uuid + "_" + file.getOriginalFilename();

                File destinationFile = new File(uploadFolder + imgFileName);

                try {
                    file.transferTo(destinationFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                CoupleBoardImage img = CoupleBoardImage.builder()
                        .imgUrl("/coupleBoardImgs/" + imgFileName)
                        .coupleBoard(result)
                        .build();

                imageRepository.save(img);
            }
        }
        return result.getPostId();
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
    public List<CoupleBoardDto.Response> searchBoardList(LocalDate createAt) {
        List<CoupleBoard> boardList = coupleBoardRepository.findAllByCreatedDate(createAt);
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
