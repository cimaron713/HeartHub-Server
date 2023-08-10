package com.umc_spring.Heart_Hub.board.dto.community;

import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.board.model.community.BoardGood;
import com.umc_spring.Heart_Hub.board.model.community.BoardImg;
import com.umc_spring.Heart_Hub.board.model.community.Comment;
import com.umc_spring.Heart_Hub.user.model.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class BoardDto {

    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    public static class BoardRequestDto{
        private String content;
        private String theme;
        private String userName;
        private Long boardId;
    }

    @Getter
    @NoArgsConstructor
    public static class BoardResponseDto {
        private Long boardId;
        private String content;
        private String theme;
        private String userName;
        private String nickName;
        private List<CommentDto.Response> commentList;
        private List<String> communityImgUrl;
        private LocalDate createdDate;
        private String userImgUrl;
        @Builder
        public BoardResponseDto(Board board){
            this.boardId = board.getBoardId();
            this.content = board.getContent();
            this.theme = board.getTheme();
            this.userName = board.getUser().getUsername();
            this.nickName = board.getUser().getNickname();
            this.commentList = board.getComments().stream().map(CommentDto.Response::new).collect(Collectors.toList());
            this.communityImgUrl = board.getCommunity().stream().map(BoardImg::getPostImgUrl).collect(Collectors.toList());
            this.createdDate = board.getCreatedDate();
            this.userImgUrl = board.getUser().getUserImgUrl();
        }



    }

    @Getter
    @NoArgsConstructor
    public static class BlockUserReqDto {
        private String blockedUserName;
    }

    @Getter
    @NoArgsConstructor
    public static class BlockUserResDto {
        private String blockedUserName;

        @Builder
        public BlockUserResDto(User user) {
            this.blockedUserName = user.getUsername();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class UnblockUserReqDto {
        private String blockedUserName;
    }

    @Getter
    @NoArgsConstructor
    public static class UnblockUserResDto {
        private String blockedUserName;

        @Builder
        public UnblockUserResDto(User user) {
            this.blockedUserName = user.getUsername();
        }
    }

}
