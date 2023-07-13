package com.umc_spring.Heart_Hub.board.dto.community;


import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.board.model.community.BoardGood;
import com.umc_spring.Heart_Hub.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



public class BoardGoodDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{
        Long userId;
        Long boardId;
    }
    @Getter
    @NoArgsConstructor
    public static class Response{
        private Long goodId;
        private User user;
        private Board board;
        private String status;
        @Builder
        public Response(BoardGood boardGood){
            this.goodId = boardGood.getGoodId();
            this.user = boardGood.getUser();
            this.board = boardGood.getBoard();
            this.status = boardGood.getStatus();
        }
    }
}
