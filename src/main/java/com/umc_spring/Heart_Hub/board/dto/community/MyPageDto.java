package com.umc_spring.Heart_Hub.board.dto.community;

import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.user.model.User;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class MyPageDto {
    @Getter
    @Builder
    public static class Request{
        private String myImgUrl;
        private User user;
    }

    public static class Response{
        private String myImgUrl;
        private List<Board> myBoards;
        private User user;
        private String userName;
        private String userMessage;

        @Builder
        public Response(User user){
            this.myImgUrl = user.getUserImgUrl();
            this.myBoards = user.getBoardList();
            this.user = user;
        }
    }
}
