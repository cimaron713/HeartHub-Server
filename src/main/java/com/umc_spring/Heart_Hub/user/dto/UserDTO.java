package com.umc_spring.Heart_Hub.user.dto;

import com.umc_spring.Heart_Hub.board.dto.community.BoardDto;
import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.user.model.User;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


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
        //private LocalDateTime birth;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequest{
        private String email;
        private String password;

        public UsernamePasswordAuthenticationToken toAuthentication(){
            return new UsernamePasswordAuthenticationToken(this.email, this.password);
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
    public static class LoginResponse{
        private String token;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetUserInfoRequest {
        private Long userId;
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
                    .collect(Collectors.toList());

            return new GetUserInfoResponse(boardList, user);
        }

    }
}
