package com.umc_spring.Heart_Hub.board.dto.community;

import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.user.model.User;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyPageDto {
    @Getter
    @Builder
    public static class Request{
        private String myImgUrl;
        private String userName;
    }

    public static class Response{
        private String myImgUrl;
        private List<BoardDto.BoardResponseDto> myBoards;
        private String userName;
        private String userMessage;

        @Builder
        public Response(User user){
            this.myImgUrl = user.getUserImgUrl();
            this.myBoards = Collections.singletonList((BoardDto.BoardResponseDto) user.getBoardList());
            this.userName = user.getNickname();
            this.userMessage = user.getUserMessage();
        }
    }
}
