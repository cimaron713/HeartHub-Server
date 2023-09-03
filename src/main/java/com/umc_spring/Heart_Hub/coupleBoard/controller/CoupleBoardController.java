package com.umc_spring.Heart_Hub.coupleBoard.controller;

import com.umc_spring.Heart_Hub.board.dto.community.BoardDto;
import com.umc_spring.Heart_Hub.board.service.community.BoardHeartService;
import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import com.umc_spring.Heart_Hub.constant.enums.CustomResponseStatus;
import com.umc_spring.Heart_Hub.coupleBoard.dto.CoupleBoardDto;
import com.umc_spring.Heart_Hub.coupleBoard.dto.CoupleBoardImageUploadDto;
import com.umc_spring.Heart_Hub.coupleBoard.service.CoupleBoardService;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class CoupleBoardController {

    private static final Logger logger = LoggerFactory.getLogger(CoupleBoardController.class);

    private final CoupleBoardService coupleBoardService;
    private final BoardHeartService boardHeartService;
    private final UserRepository userRepository;

    /**
     * 게시물 작성
     */
    @PostMapping(value = "/couple-board/write", consumes = "multipart/*")
    public ResponseEntity<ApiResponse<String>> createBoard(@RequestPart(value = "requestDto", required = false) CoupleBoardDto.Request requestDto,
                                                           @RequestPart(value = "files", required = false) MultipartFile[] files,
                                                           Authentication authentication) throws IOException {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        CoupleBoardImageUploadDto boardImageUploadDto = new CoupleBoardImageUploadDto();
        logger.info("boardImageDTO is {}", boardImageUploadDto);
        boardImageUploadDto.setFiles(files);
        Long postId = coupleBoardService.saveBoard(requestDto, boardImageUploadDto, userDetails.getUsername());

        return ResponseEntity.ok().body(ApiResponse.createSuccess(postId.toString(), CustomResponseStatus.SUCCESS));
    }

    /**
     * 해당 날짜에 작성한 앨범 조회
     */
    @GetMapping("/couple-board/{createAt}")
    public ResponseEntity<ApiResponse<List<CoupleBoardDto.Response>>> getBoardsByDate(@PathVariable LocalDate createAt, Authentication authentication) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        List<CoupleBoardDto.Response> boardList = coupleBoardService.searchBoardList(createAt, username);
        Collections.sort(boardList, Comparator.comparing(CoupleBoardDto.Response::getCreateAt));

        return ResponseEntity.ok().body(ApiResponse.createSuccess(boardList, CustomResponseStatus.SUCCESS));
    }

    /**
     * 게시물 상세 조회
     */
    @GetMapping("/couple-board/detail/{postId}")
    public ResponseEntity<ApiResponse<List<CoupleBoardDto.DetailResponse>>> getBoardsDetail(@PathVariable Long postId, Authentication authentication) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        List<CoupleBoardDto.DetailResponse> boardList = coupleBoardService.searchDetailBoard(postId, username);
        Collections.sort(boardList, Comparator.comparing(CoupleBoardDto.DetailResponse::getCreateAt));

        return ResponseEntity.ok().body(ApiResponse.createSuccess(boardList, CustomResponseStatus.SUCCESS));
    }


    /**
     * 게시물 수정
     */
    @PutMapping("/couple-board/{postId}/update")
    public ResponseEntity<ApiResponse<String>> updateBoard(@PathVariable Long postId, @RequestBody CoupleBoardDto.Request requestDto,
                                                           Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (!coupleBoardService.isAuthorized(postId, userDetails.getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.createError(CustomResponseStatus.AUTHORIZATION_FAILED));
        }

        coupleBoardService.updateBoard(postId, requestDto);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(postId.toString(), CustomResponseStatus.SUCCESS));
    }

    /**
     * 게시물 삭제
     */
    @DeleteMapping("/couple-board/{postId}/delete")
    public ResponseEntity<ApiResponse<String>> deleteBoard(@PathVariable Long postId, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (!coupleBoardService.isAuthorized(postId, userDetails.getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.createError(CustomResponseStatus.AUTHORIZATION_FAILED));
        }

        coupleBoardService.deleteBoard(postId);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(postId.toString(), CustomResponseStatus.SUCCESS));
    }

    /**
     * 자신이 스크랩한 게시물 조회
     */
    @GetMapping("/scrap-board")
    public ResponseEntity<ApiResponse<List<BoardDto.BoardResponseDto>>> scrapBoard(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<BoardDto.BoardResponseDto> scrapBoardList = boardHeartService.getHeartBoards(userDetails.getUsername());

        return ResponseEntity.ok().body(ApiResponse.createSuccess(scrapBoardList, CustomResponseStatus.SUCCESS));
    }

    /**
     * 연동한 계정이 스크랩한 게시물 조회
     */
    @GetMapping("/scrap-mate-board")
    public ResponseEntity<ApiResponse<List<BoardDto.BoardResponseDto>>> scrapMateBoard(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<BoardDto.BoardResponseDto> scrapBoardList = boardHeartService.getHeartMateBoards(userDetails.getUsername());

        return ResponseEntity.ok().body(ApiResponse.createSuccess(scrapBoardList, CustomResponseStatus.SUCCESS));
    }

}
