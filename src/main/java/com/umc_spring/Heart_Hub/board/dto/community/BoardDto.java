package com.umc_spring.Heart_Hub.board.dto.community;

import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.user.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class BoardDto {
    @Getter
    @NoArgsConstructor
    public static class BoardRequestDto{
        private String content;
        private User user;
        @Builder
        public Board toEntity(){
            return Board.builder()
                    .content(content)
                    .user(user)
                    .build();
        }
    }
    @Getter
    public static class BoardResponseDto {
        private final Long boardId;

        private String content;

        private String status;

        private User user;

        public BoardResponseDto(Board board){
            this.boardId = board.getBoardId();
            this.content = board.getContent();
            this.status = board.getStatus();
            this.user = board.getUser();
        }
    }

}
