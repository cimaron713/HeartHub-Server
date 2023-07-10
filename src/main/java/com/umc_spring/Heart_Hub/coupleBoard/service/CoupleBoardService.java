package com.umc_spring.Heart_Hub.coupleboard.service;

import com.umc_spring.Heart_Hub.coupleBoard.dto.BoardDto;
import com.umc_spring.Heart_Hub.coupleBoard.dto.BoardImageUploadDto;

import java.time.LocalDate;
import java.util.List;

public interface CoupleBoardService {

    /**
     * 게시물 작성
     * @param requestDto 게시물 작성 정보
     * @param boardImageUploadDto 게시물 사진
     * @param userName 사용자 아이디
     * @return 게시물 ID
     */
    Long saveBoard(BoardDto.Request requestDto, BoardImageUploadDto boardImageUploadDto, String userName);

    /**
     * 게시물 상세 조회
     * @param postId 게시물 ID
     * @return 게시물 정보
     */
    BoardDto.Response detailBoard(Long postId);

    /**
     * 날짜에 따른 게시물 조회
     * @param createAt
     * @return
     */
    List<BoardDto.Response> searchBoardList(LocalDate createAt);

    /**
     * 게시물 수정
     * @param postId 게시물 ID
     * @param requestDto 수정 정보
     * @return 게시물 ID
     */
    Long updateBoard(Long postId, BoardDto.Request requestDto);

    /**
     * 게시물 삭제
     * @param postId
     */
    void deleteBoard(Long postId);


}
