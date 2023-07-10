package com.umc_spring.Heart_Hub.board.dto;

import com.umc_spring.Heart_Hub.board.model.Board;
import com.umc_spring.Heart_Hub.user.model.User;
import lombok.Getter;

@Getter
public class BoardResponseDto {
    private final Long boardId;

    private String content;

    private String status;

    private User user;


    public BoardResponseDto(Board entity){
        this.boardId = entity.getBoardId();
        this.content = entity.getContent();
        this.status = entity.getStatus();
        this.user = entity.getUser();
    }
}