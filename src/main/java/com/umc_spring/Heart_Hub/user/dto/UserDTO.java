package com.umc_spring.Heart_Hub.user.dto;

import com.umc_spring.Heart_Hub.board.dto.community.BoardDto;
import com.umc_spring.Heart_Hub.user.model.User;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class UserDTO {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignUpRequest{
        private String username;
        private String id;
        private String password;
        private String gender;
        private String email;
        private String nickname;
        private String marketingStatus;
        private LocalDate birth;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequest{
        private String id;
        private String password;

        public UsernamePasswordAuthenticationToken toAuthentication(){
            return new UsernamePasswordAuthenticationToken(this.id, this.password);
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DuplicateEmailCheckRequest{
        private String email;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DuplicateIdCheckRequest{
        private String id;
    }
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginResponse{
        private String token;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class sendVerificationCode{
        private String email;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class emailVerification{
        private String code;
    }
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetUserInfoRequest {
        private Long userId;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class findIdRequest{
        private String email;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class findPwRequest{
        private String email;
        private String id;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MateMatchRequest{
        private String currentUserId;
        private String mateId;
    }

    @Getter
    @NoArgsConstructor
    public static class GetUserInfoResponse {
        private String username;
        private String nickname;
        private String userImgUrl;
        private List<BoardDto.BoardResponseDto> boardList;
        public GetUserInfoResponse(List<BoardDto.BoardResponseDto> boardList, User user) {
            this.username = user.getUsername();
            this.nickname = user.getNickname();
            this.userImgUrl = user.getUserImgUrl();
            this.boardList = boardList;
        }

        public static GetUserInfoResponse of(User user) {
            List<BoardDto.BoardResponseDto> boardList = user.getBoardList().stream()
                    .map(board -> new BoardDto.BoardResponseDto(board))
                    .sorted(Comparator.comparing(BoardDto.BoardResponseDto::getCreatedDate).reversed())
                    .collect(Collectors.toList());

            return new GetUserInfoResponse(boardList, user);
        }

    }

    @Getter
    @NoArgsConstructor
    public static class GetDday {
        private LocalDate dDay;
        @Builder
        public GetDday(LocalDate dDay) {
            this.dDay = dDay;
        }
    }
}
