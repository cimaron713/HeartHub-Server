package com.umc_spring.Heart_Hub.coupleBoard.dto;

import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.coupleBoard.model.CoupleBoard;
import com.umc_spring.Heart_Hub.coupleBoard.model.CoupleBoardImage;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CoupleBoardDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String title;
        private String content;

    }

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long postId;
        private Long userId;
        private String status;
        private String title;
        private String content;
        private LocalDate createAt;
        private String userName;
        private List<String> imageUrls;

        @Builder
        public Response(CoupleBoard board) {
            this.postId = board.getPostId();
            this.userId = board.getUser().getUserId();
            this.status = board.getStatus();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.createAt = board.getCreatedDate();
            this.userName = board.getUser().getUsername();
            this.imageUrls = board.getBoardImages().stream()
                    .map(CoupleBoardImage::getImgUrl)
                    .collect(Collectors.toList());
        }
    }

}
