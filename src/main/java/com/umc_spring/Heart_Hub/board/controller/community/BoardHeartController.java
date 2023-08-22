package com.umc_spring.Heart_Hub.board.controller.community;


import com.umc_spring.Heart_Hub.board.dto.community.BoardGoodDto;
import com.umc_spring.Heart_Hub.board.dto.community.BoardHeartDto;
import com.umc_spring.Heart_Hub.board.service.community.BoardHeartService;
import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import com.umc_spring.Heart_Hub.constant.enums.CustomResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/user/board")
@RequiredArgsConstructor
public class BoardHeartController {
    private final BoardHeartService boardHeartService;

    @PostMapping("/heart")
    public ResponseEntity<ApiResponse<String>> heart(@RequestPart BoardHeartDto.Request request,
                                                     Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        boardHeartService.heartRegister(request, userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/{boardId}/heart/check")
    public ResponseEntity<ApiResponse<BoardHeartDto.heartCheckResponse>> heartCheck(@PathVariable(value = "boardId") Long boardId,
                                                          Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        BoardHeartDto.heartCheckResponse response = boardHeartService.heartCheck(userDetails.getUsername(), boardId);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }
}
