package com.umc_spring.Heart_Hub.board.controller.community;


import com.umc_spring.Heart_Hub.board.dto.community.BoardDto;
import com.umc_spring.Heart_Hub.board.dto.community.BoardHeartDto;
import com.umc_spring.Heart_Hub.board.service.community.BoardHeartService;
import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import com.umc_spring.Heart_Hub.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/user/board")
public class BoardHeartController {
    private BoardHeartService boardHeartService;

    @PostMapping("/{boardId}/heart")
    public ResponseEntity<ApiResponse<String>> heart(@PathVariable Long boardId, @RequestBody BoardHeartDto.Request params,
                                                     Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        boardHeartService.heartRegister(params, userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent("Register Heart Success"));
    }


}
