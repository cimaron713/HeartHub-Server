package com.umc_spring.Heart_Hub.board.service.community;

import com.umc_spring.Heart_Hub.board.dto.community.BoardDto;
import com.umc_spring.Heart_Hub.board.dto.community.BoardImageUploadDto;
import com.umc_spring.Heart_Hub.user.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BoardService {

    Long boardRegister(BoardDto.BoardRequestDto params, String userName, BoardImageUploadDto boardImageUploadDto);
    Long boardWritingRegister(BoardDto.BoardRequestDto params, String userName);
    List<String> upload(MultipartFile[] multipartFile, String username) throws IOException;
    List<BoardDto.BoardResponseDto> findAll(String theme);
    List<BoardDto.BoardResponseDto> findUserBoards(String userName, String theme);
    BoardDto.BoardResponseDto findBoard(Long id);
    BoardDto.BoardResponseDto updateBoard(BoardDto.BoardRequestDto requestDto, String userName);
    void delBoard(BoardDto.BoardRequestDto requestDto, String userName);
    String getLoginUsername();
}
