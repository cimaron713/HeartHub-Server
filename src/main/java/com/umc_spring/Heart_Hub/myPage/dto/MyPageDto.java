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
        private String userMessage;
        private String userNickName;
    }
    @Getter
    @NoArgsConstructor
    public static class Response{
        private String userMessage;

        @Builder
        public Response(User user){
            this.userMessage = user.getUserMessage();
        }
    }
    @NoArgsConstructor
    @Getter
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
