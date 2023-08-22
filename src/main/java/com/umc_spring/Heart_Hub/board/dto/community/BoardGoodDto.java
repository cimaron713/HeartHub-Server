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
        Long userName;
    }
    @Getter
    @NoArgsConstructor
    public static class Response{
        private Long goodId;
        private String userName;
        private Long boardId;
        private String status;
        @Builder
        public Response(BoardGood boardGood){
            this.goodId = boardGood.getGoodId();
            this.userName = boardGood.getUser().getUsername();
            this.boardId = boardGood.getBoard().getBoardId();
            this.status = boardGood.getStatus();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class goodCheckResponse{
        private String status;
        @Builder
        public goodCheckResponse(String status){
            this.status = status;
        }
    }
}
