package com.umc_spring.Heart_Hub.board.dto.community;

import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.board.model.community.Comment;
import com.umc_spring.Heart_Hub.user.dto.UserDTO;
import com.umc_spring.Heart_Hub.user.model.User;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.umc_spring.Heart_Hub.board.model.community.QComment.comment;

public class CommentDto {
    private UserRepository userRepository;
    @Data
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request{
        private Long boardId;
        private String userName;
        private String content;
        private Long parentId;

        public Request(String content){
            this.content = content;
        }
    }

    @Getter
    public static class Response{
        private Long commentId;
        private String userName;
        private String content;
        private List<CommentDto.Response> responseList = new ArrayList<>();
        private int count;

        @Builder
        public Response(Comment comment){
            this.commentId = comment.getCommentId();
            this.userName = comment.getUser().getUsername();
            this.content = comment.getContent();
            this.count = comment.getGoods().size();
        }

        public static Response convertCommentToDto(Comment comment) {

            return comment.getStatus().equals("N") ?
                    new CommentDto.Response(comment) :
                    new CommentDto.Response(comment);
        }

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DeleteRequest {
        private Long commentId;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DeleteResponse {
        private Long deletedCommentId;
    }


}
