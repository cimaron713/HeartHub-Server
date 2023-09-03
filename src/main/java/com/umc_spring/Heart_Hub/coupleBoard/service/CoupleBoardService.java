package com.umc_spring.Heart_Hub.coupleBoard.service;

import com.umc_spring.Heart_Hub.coupleBoard.dto.CoupleBoardDto;
import com.umc_spring.Heart_Hub.coupleBoard.dto.CoupleBoardImageUploadDto;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface CoupleBoardService {

    boolean isAuthorized(Long postId, String username);

    /**
     * 게시물 작성
     * @param requestDto 게시물 작성 정보
     * @param boardImageUploadDto 게시물 사진
     * @param userName 사용자 아이디
     * @return 게시물 ID
     */
    Long saveBoard(CoupleBoardDto.Request requestDto, CoupleBoardImageUploadDto boardImageUploadDto, String userName) throws IOException;

    /**
     * 날짜에 따른 앨범 목록 조회
     * @param createAt
     * @return
     */
    List<CoupleBoardDto.Response> searchBoardList(LocalDate createAt, String username);

    /**
     * 앨범 상세 조회
     * @param postId
     * @param username
     * @return
     */
    List<CoupleBoardDto.DetailResponse> searchDetailBoard(Long postId, String username);

    /**
     * 앨범 수정
     * @param postId 게시물 ID
     * @param requestDto 수정 정보
     * @return 게시물 ID
     */
    void updateBoard(Long postId, CoupleBoardDto.Request requestDto);

    /**
     * 앨범 삭제
     * @param postId
     */
    void deleteBoard(Long postId);


}
