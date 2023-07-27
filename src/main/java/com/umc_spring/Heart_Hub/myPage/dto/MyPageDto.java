package com.umc_spring.Heart_Hub.myPage.dto;

import com.umc_spring.Heart_Hub.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MyPageDto {
    @Getter
    @AllArgsConstructor
    public static class Request{
        private String myImgUrl;
        private String userName;
        private String userMessage;
    }
    @Getter
    @NoArgsConstructor
    public static class Response{
        private String myImgUrl;
        private String userName;
        private String userMessage;

        @Builder
        public Response(User user){
            this.myImgUrl = user.getUserImgUrl();
            this.userName = user.getNickname();
            this.userMessage = user.getUserMessage();
        }
    }
    @NoArgsConstructor
    public static class MyPage{
        private String myImgUrl;
        private String userName;
        @Builder
        public MyPage(User user){
            this.myImgUrl = user.getUserImgUrl();
            this.userName = user.getNickname();
        }
    }
}
