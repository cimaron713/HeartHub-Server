package com.umc_spring.Heart_Hub.board.controller.community;

import com.umc_spring.Heart_Hub.board.dto.community.BoardDto;
import com.umc_spring.Heart_Hub.board.dto.community.BoardGoodDto;
import com.umc_spring.Heart_Hub.board.service.community.BoardGoodService;
import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/board")
public class BoardGoodContoller {
    private BoardGoodService boardGoodService;
    //좋아요 등록
    @PostMapping("/{boardId}/good")
    public ResponseEntity<ApiResponse<String>> good(@PathVariable Long boardId, @RequestBody BoardDto.BoardRequestDto params,
                                                    Authentication authentication){
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        boardGoodService.goodRegister(params, userDetails.getUsername(),boardId);
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent("register good success"));
    }

    @GetMapping("/{id}/good/count")
    public ResponseEntity<ApiResponse<Integer>> goodCount(@PathVariable Long id,@RequestBody BoardGoodDto.Request params){
        int goodCnt = boardGoodService.goodCount(id);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(goodCnt,"Count good success"));
    }

}
