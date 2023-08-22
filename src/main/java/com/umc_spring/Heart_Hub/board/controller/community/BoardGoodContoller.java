package com.umc_spring.Heart_Hub.board.controller.community;

import com.umc_spring.Heart_Hub.board.dto.community.BoardDto;
import com.umc_spring.Heart_Hub.board.dto.community.BoardGoodDto;
import com.umc_spring.Heart_Hub.board.service.community.BoardGoodService;
import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import com.umc_spring.Heart_Hub.constant.enums.CustomResponseStatus;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/board")
public class BoardGoodContoller {
    private final BoardGoodService boardGoodService;
    //좋아요 등록
    @PostMapping("/{boardId}/good")
    public ResponseEntity<ApiResponse<String>> good(@PathVariable(value = "boardId") Long boardId,
                                                    Authentication authentication){
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        boardGoodService.goodRegister(userDetails.getUsername(),boardId);
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/{boardId}/good/count")
    public ResponseEntity<ApiResponse<Integer>> goodCount(@PathVariable(value = "boardId") Long boardId){
        int goodCnt = boardGoodService.goodCount(boardId);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(goodCnt,CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/{boardId}/good/check")
    public ResponseEntity<ApiResponse<BoardGoodDto.goodCheckResponse>> goodCheck(@PathVariable(value = "boardId") Long boardId,
                                                         Authentication authentication){
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        BoardGoodDto.goodCheckResponse response = boardGoodService.goodCheck(userDetails.getUsername(), boardId);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

}
