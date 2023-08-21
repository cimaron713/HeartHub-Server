package com.umc_spring.Heart_Hub.myPage.controller;

import com.umc_spring.Heart_Hub.board.dto.community.BoardDto;
import com.umc_spring.Heart_Hub.board.service.community.BoardService;
import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import com.umc_spring.Heart_Hub.constant.enums.CustomResponseStatus;
import com.umc_spring.Heart_Hub.myPage.dto.MyPageDto;
import com.umc_spring.Heart_Hub.myPage.dto.MyPageImgUploadDto;
import com.umc_spring.Heart_Hub.myPage.service.MyPageService;
import com.umc_spring.Heart_Hub.user.model.User;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import com.umc_spring.Heart_Hub.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/myPage")
@Slf4j
public class MyPageController {
    private final UserService userService;
    private final BoardService boardService;
    private final MyPageService myPageService;
    private final UserRepository userRepository;
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

    @PutMapping(value = "/update",consumes = "multipart/*")
    public ResponseEntity<ApiResponse<Boolean>> myProfileUpdate(@RequestPart(value = "request") MyPageDto.Request request,
                                                               @RequestPart(value = "file") MultipartFile file,
                                                               Authentication authentication){
        String userName = ((UserDetails) authentication.getPrincipal()).getUsername();
        MyPageImgUploadDto myPageImgUploadDto = new MyPageImgUploadDto();
        myPageImgUploadDto.setFiles(file);
        User user = userRepository.findByUsername(userName);
        if(user.getNickname().equals(request.getUserNickName()) || !userService.validateDuplicateNickname(request.getUserNickName())) {
            MyPageDto.Response response = myPageService.myPageProfileUpdate(userName, request, myPageImgUploadDto);
            return ResponseEntity.ok().body(ApiResponse.createSuccess(true, CustomResponseStatus.SUCCESS));
        }
        return ResponseEntity.ok().body(ApiResponse.createSuccess(false, CustomResponseStatus.DUPLICATION_NICKNAME));
    }
}
