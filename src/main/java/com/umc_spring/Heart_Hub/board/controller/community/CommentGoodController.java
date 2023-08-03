package com.umc_spring.Heart_Hub.board.controller.community;

import com.umc_spring.Heart_Hub.board.dto.community.CommentDto;
import com.umc_spring.Heart_Hub.board.dto.community.CommentGoodDto;
import com.umc_spring.Heart_Hub.board.service.community.CommentGoodService;
import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import com.umc_spring.Heart_Hub.constant.enums.CustomResponseStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentGoodController {
    private CommentGoodService commentGoodService;
    @PostMapping("/api/user/board/{commentId}/good")
    public ResponseEntity<ApiResponse<String>> commentGood(@PathVariable Long commentId,
                                                           @RequestBody CommentDto.Request request, Authentication authentication){
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        commentGoodService.commentGood(commentId,request, userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/api/user/board/{commentId}")
    public ResponseEntity<ApiResponse<Integer>> commentGoodCount(@PathVariable Long commentId){
        int cnt = commentGoodService.commentGoodCnt(commentId);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(cnt, CustomResponseStatus.SUCCESS));
    }
}
