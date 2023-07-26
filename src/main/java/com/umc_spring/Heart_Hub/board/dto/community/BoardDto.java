package com.umc_spring.Heart_Hub.board.dto.community;

import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.board.model.community.BoardImg;
import com.umc_spring.Heart_Hub.user.model.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class BoardDto {
    @Getter
    @Data
    @NoArgsConstructor
    public static class BoardRequestDto{
        private String content;
        private String theme;
        private String userName;
        private Long boardId;
        private List<CommentDto.Request> commentList;
    }

    @Getter
    public static class BoardResponseDto {
        private final Long boardId;
        private String content;
        private String goodStatus;
        private String heartStatus;
        private String theme;
        private String userName;
        private List<CommentDto.Response> commentList;
        private List<String> communityImgUrl;
        private LocalDateTime createdDate;
        @Builder
        public BoardResponseDto(Board board){
            this.boardId = board.getBoardId();
            this.content = board.getContent();
            this.goodStatus = board.getGoodStatus();
            this.heartStatus = board.getHeartStatus();
            this.theme = board.getTheme();
            this.userName = board.getUser().getUsername();
            this.commentList = board.getComments().stream().map(CommentDto.Response::new).collect(Collectors.toList());
            this.communityImgUrl = board.getCommunity().stream().map(BoardImg::getPostImgUrl).collect(Collectors.toList());
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
