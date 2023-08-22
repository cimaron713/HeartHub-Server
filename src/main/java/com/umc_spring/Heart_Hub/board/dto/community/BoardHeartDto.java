package com.umc_spring.Heart_Hub.board.dto.community;

import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.board.model.community.BoardHeart;
import com.umc_spring.Heart_Hub.user.model.User;
import lombok.*;

public class BoardHeartDto {

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
        private Long heartId;
        private String userName;
        private Long boardId;
        private String status;
        @Builder
        public Response(BoardHeart boardHeart){
            this.heartId = boardHeart.getHeartId();
            this.userName = boardHeart.getUser().getUsername();
            this.boardId = boardHeart.getBoard().getBoardId();
            this.status = boardHeart.getStatus();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class heartCheckResponse{
        private String status;
        @Builder
        public heartCheckResponse(String status){
            this.status = status;
        }
    }
}
