package com.umc_spring.Heart_Hub.board.controller.community;

import com.umc_spring.Heart_Hub.board.dto.community.BoardGoodDto;
import com.umc_spring.Heart_Hub.board.service.community.BoardGoodService;
import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/board/heart")
public class BoardGoodContoller {
    private BoardGoodService boardGoodService;
    //좋아요 등록
    @PostMapping
    public ResponseEntity<ApiResponse<String>> goodRegister(@RequestBody BoardGoodDto params){
        boardGoodService.goodRegister(params);
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent("register good success"));
    }

    //좋아요 취소
    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> goodCancle(@RequestBody BoardGoodDto params) {
        boardGoodService.goodCancle(params);
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent("cancle good success"));
    }

    @GetMapping("/{id}/counts")
    public ResponseEntity<ApiResponse<Integer>> goodCount(@RequestBody BoardGoodDto params){
        int goodCnt = boardGoodService.goodCount(params.getBoardId());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(goodCnt,"Count good success"));
    }

}
