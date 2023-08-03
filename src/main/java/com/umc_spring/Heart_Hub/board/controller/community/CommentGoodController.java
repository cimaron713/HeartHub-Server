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
    @PostMapping("/api/user/board/comment/{commentId}/good")
    public ResponseEntity<ApiResponse<String>> commentGood(@PathVariable Long commentId, Authentication authentication){
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        commentGoodService.commentGood(commentId, userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/api/user/board/comment/{commentId}/counts")
    public ResponseEntity<ApiResponse<String>> commentGoodCount(@PathVariable Long commentId){
        String cnt = commentGoodService.commentGoodCnt(commentId);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(cnt, CustomResponseStatus.SUCCESS));
    }
}
