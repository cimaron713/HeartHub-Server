package com.umc_spring.Heart_Hub.board.service.community;

import com.umc_spring.Heart_Hub.board.dto.community.BoardDto;
import com.umc_spring.Heart_Hub.board.dto.community.BoardHeartDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BoardHeartService {
    void heartRegister(BoardHeartDto.Request params, String userName);
    List<BoardDto.BoardResponseDto> getHeartBoards(String username);
    List<BoardDto.BoardResponseDto> getHeartMateBoards(String username);
    List<BoardDto.BoardResponseDto> getHeartBoardsByUsername(String username);
}
