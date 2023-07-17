package com.umc_spring.Heart_Hub.board.dto.community;

import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.board.model.community.Comment;
import com.umc_spring.Heart_Hub.user.model.User;
import lombok.*;

import java.util.stream.Collectors;

public class CommentDto {
    @Data
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{
        private Board board;
        private User user;
        private String content;


        public Comment Request(){
            return Comment.builder()
                    .board(board)
                    .user(user)
                    .content(content)
                    .build();
        }

    }
    @Getter
    public static class Response{
        private Long commentId;
        private Board board;
        private User user;
        private String content;

        @Builder
        public Response(Comment comment){
            this.commentId = comment.getCommentId();
            this.user = comment.getUser();
            this.board = comment.getBoard();
            this.content = comment.getContent();
        }
    }
}
