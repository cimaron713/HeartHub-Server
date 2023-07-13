package com.umc_spring.Heart_Hub.board.dto.community;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardGoodDto {
    private Long userId;
    private Long boardId;
    private String status;

    public BoardGoodDto (Long userId, Long boardId){
        this.userId = userId;
        this.boardId = boardId;
    }
}
