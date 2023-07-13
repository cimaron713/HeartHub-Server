package com.umc_spring.Heart_Hub.board.dto;

import com.umc_spring.Heart_Hub.board.model.Board;
import com.umc_spring.Heart_Hub.user.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
