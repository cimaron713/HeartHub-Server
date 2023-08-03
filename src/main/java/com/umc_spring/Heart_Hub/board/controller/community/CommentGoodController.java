package com.umc_spring.Heart_Hub.board.controller.community;

import com.umc_spring.Heart_Hub.board.dto.community.CommentDto;
import com.umc_spring.Heart_Hub.board.dto.community.CommentGoodDto;
import com.umc_spring.Heart_Hub.board.service.community.CommentGoodService;
import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import com.umc_spring.Heart_Hub.constant.enums.CustomResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentGoodController {
    private final CommentGoodService commentGoodService;
    /**
     * 게시글 좋아요
     */
    @PostMapping("/api/user/board/comment/good")
    public ResponseEntity<ApiResponse<String>> commentGood(@RequestBody CommentGoodDto.goodRequest request, Authentication authentication){
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        commentGoodService.commentGood(request.getCommentId(), userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS));
    }

    /**
     * 좋아요 취소
     * @param request
     * @param authentication
     * @return
     */
    @DeleteMapping("/api/user/board/comment/unGood")
    public ResponseEntity<ApiResponse<String>> deleteGood(@RequestBody CommentGoodDto.goodRequest request, Authentication authentication){
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        commentGoodService.commentGoodDelete(request.getCommentId(), userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/api/user/board/comment/{commentId}/counts")
    public ResponseEntity<ApiResponse<String>> commentGoodCount(@PathVariable Long commentId){
        String cnt = commentGoodService.commentGoodCnt(commentId);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(cnt, CustomResponseStatus.SUCCESS));
    }
}
