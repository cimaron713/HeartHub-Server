package com.umc_spring.Heart_Hub.myPage.controller;

import com.umc_spring.Heart_Hub.board.dto.community.BoardDto;
import com.umc_spring.Heart_Hub.board.service.community.BoardService;
import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import com.umc_spring.Heart_Hub.constant.enums.CustomResponseStatus;
import com.umc_spring.Heart_Hub.myPage.dto.MyPageDto;
import com.umc_spring.Heart_Hub.myPage.service.MyPageService;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import com.umc_spring.Heart_Hub.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/myPage")
public class MyPageController {
    private UserService userService;
    private BoardService boardService;
    private MyPageService myPageService;
    private UserRepository userRepository;
    @GetMapping("/first")
    public ResponseEntity<ApiResponse<MyPageDto.MyPage>> myPageMenu(Authentication authentication){
        String userName = ((UserDetails) authentication.getPrincipal()).getUsername();
        MyPageDto.MyPage userProfile = myPageService.myProfileMenu(userName);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(userProfile, CustomResponseStatus.SUCCESS));
    }
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<MyPageDto.Response>> readMyPage(Authentication authentication){
        String userName = ((UserDetails) authentication.getPrincipal()).getUsername();
        MyPageDto.Response result = myPageService.myPageDetail(userName);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(result,CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/{theme}/boards")
    public ResponseEntity<ApiResponse<List<BoardDto.BoardResponseDto>>> myPageBoard(@PathVariable String theme, Authentication authentication){
        String userName = ((UserDetails) authentication.getPrincipal()).getUsername();
        List<BoardDto.BoardResponseDto> boardResult = boardService.findUserBoards(userName,theme);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(boardResult,CustomResponseStatus.SUCCESS));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<String>> myProfileUpdate(@RequestBody MyPageDto.Request request, Authentication authentication){
        String userName = ((UserDetails) authentication.getPrincipal()).getUsername();
        myPageService.myPageProfileUpdate(userName, request);
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS));
    }
}
