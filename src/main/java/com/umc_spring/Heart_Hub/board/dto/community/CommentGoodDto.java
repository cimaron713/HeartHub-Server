package com.umc_spring.Heart_Hub.board.dto.community;

import com.umc_spring.Heart_Hub.board.model.community.Comment;
import com.umc_spring.Heart_Hub.board.model.community.CommentGood;
import com.umc_spring.Heart_Hub.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentGoodDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{
        private String userName;
        private Long commentId;
    }
    @Getter
    @NoArgsConstructor
    public static class Response{
        private Long commentGoodId;
        private String userName;
        private Long commentId;
        private String status;

        @Builder
        public Response(CommentGood commentGood){
            this.commentGoodId = commentGood.getCommentGoodId();
            this.userName = commentGood.getUser().getUsername();
            this.commentId = commentGood.getComment().getCommentId();
            this.status = commentGood.getStatus();
        }
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class goodRequest{
        private Long commentId;
    }
}
