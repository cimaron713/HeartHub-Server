package com.umc_spring.Heart_Hub.board.dto.community;

import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.board.model.community.Comment;
import com.umc_spring.Heart_Hub.user.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

public class CommentDto {
    @Data
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{
        private Board board;
        private User user;
        private String content;

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
