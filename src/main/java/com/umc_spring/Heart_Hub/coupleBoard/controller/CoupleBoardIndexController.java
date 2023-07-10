package com.umc_spring.Heart_Hub.coupleBoard.controller;

import com.umc_spring.Heart_Hub.coupleBoard.service.CoupleBoardService;
import org.springframework.stereotype.Controller;

import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import com.umc_spring.Heart_Hub.coupleBoard.dto.BoardDto;
import com.umc_spring.Heart_Hub.coupleBoard.dto.BoardImageUploadDto;
import com.umc_spring.Heart_Hub.coupleBoard.service.CoupleBoardService;
import com.umc_spring.Heart_Hub.coupleBoard.service.CoupleBoardService_before;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * 화면 연결
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class CoupleBoardIndexController {

    private final CoupleBoardService coupleBoardService;

    /**
     * 게시글 작성
     * @return 게시물 작성 페이지
     */
    @GetMapping("/write")
    public String createBoardForm() {
        return "/board/board-write";
    }

    /**
     * 게시물 상세 조회
     * @return 게시물 상세 보기 페이지
     */
    @GetMapping("/{postId}")
    public String detailBoard(@PathVariable Long postId, Model model) {
        BoardDto.Response result = coupleBoardService.detailBoard(postId);

        model.addAttribute("board", result);
        model.addAttribute("id", postId);

        return "board/board-detail";
    }

    /**
     * 날짜에 따른 게시물 조회
     */
    @GetMapping("/search/{createAt}")
    public String calendarSearch(@PathVariable LocalDate createAt, Model model, Authentication authentication) {
        List<BoardDto.Response> boardList = coupleBoardService.searchBoardList(createAt);
        Collections.sort(boardList, Comparator.comparing(BoardDto.Response::getCreateAt));

        model.addAttribute("boards", boardList);

        return "index";
    }

    /**
     * 게시물 수정
     * @return 게시물 수정 페이지
     */
    @GetMapping("/{postId}/update")
    public String updateBoardForm(@PathVariable Long postId, Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        BoardDto.Response result = coupleBoardService.detailBoard(postId);
        if(result.getUserName() != userDetails.getUsername()) {
            return "redirect:/";
        }

        model.addAttribute("board", result);
        model.addAttribute("postId", postId);

        return "board/board-update";
    }

}

