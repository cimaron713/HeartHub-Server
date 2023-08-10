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
        private String userNickName;
        private String userImgUrl;
        @Builder
        public Response(User user){
            this.userNickName = getUserNickName();
            this.userMessage = user.getUserMessage();
            this.userImgUrl = user.getUserImgUrl();
        }
    }

    @NoArgsConstructor
    @Getter
    public static class MyPage{
        private String myImgUrl;
        private String userName;
        @Builder
        public MyPage(User user, String myImgUrl){
            this.myImgUrl = myImgUrl;
            this.userName = user.getNickname();
        }
    }
}
