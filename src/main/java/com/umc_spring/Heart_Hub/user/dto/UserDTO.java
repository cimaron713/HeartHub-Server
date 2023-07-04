package com.umc_spring.Heart_Hub.user.dto;

import lombok.*;

import java.time.LocalDateTime;


public class UserDTO {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignUpRequest{
        private String username;
        private String password;
        private String gender;
        private String email;
        private String nickname;
        private String marketingStatus;
        private LocalDateTime birth;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignInRequest{
        private String email;
        private String password;
    }

}
