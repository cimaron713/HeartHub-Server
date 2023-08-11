package com.umc_spring.Heart_Hub.board.controller.community;

import com.google.rpc.context.AttributeContext;
import com.umc_spring.Heart_Hub.board.dto.community.BoardDto;

import com.umc_spring.Heart_Hub.board.dto.community.BoardImageUploadDto;
import com.umc_spring.Heart_Hub.board.service.community.BlockUserService;
import com.umc_spring.Heart_Hub.board.service.community.BoardGoodService;
import com.umc_spring.Heart_Hub.board.service.community.BoardService;
import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import com.umc_spring.Heart_Hub.constant.enums.CustomResponseStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/user/board")
public class BoardController {
    private final BoardService boardService;
    private final BlockUserService blockUserService;
    private final BoardGoodService boardGoodService;
    //게시글 작성

    //게시글 작성(사진O)
    @PostMapping(value = "/articles/write", consumes = "multipart/*")
    public ResponseEntity<ApiResponse<String>> boardWrite(@RequestPart(value = "params") BoardDto.BoardRequestDto params,
                                                        @RequestPart(value = "files") MultipartFile[] files,
                                                        Authentication authentication) throws IOException {
        log.info("files: "+files);
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        BoardImageUploadDto boardImageUploadDto = new BoardImageUploadDto();
        boardImageUploadDto.setFiles(files);
        log.info("boardImageUploadDto: "+ boardImageUploadDto);
        Long boardId = boardService.boardRegister(params, userDetails.getUsername(), boardImageUploadDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(boardId.toString(), CustomResponseStatus.SUCCESS));
    }

    //게시글 작성(사진X)
    @PostMapping("/articles/only-write")
    public ResponseEntity<ApiResponse<String>> boardWriteOnly(@RequestBody BoardDto.BoardRequestDto params,
                                                          Authentication authentication) throws IOException {
        UserDetails userDetail = (UserDetails)authentication.getPrincipal();
        Long boardId = boardService.boardWriteRegister(params, userDetail.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(boardId.toString(), CustomResponseStatus.SUCCESS));
    }

    /*
    게시글 조회
     */
    //전체
    @GetMapping("/{theme}/articles")
    public ResponseEntity<ApiResponse<List<BoardDto.BoardResponseDto>>> articleList(@PathVariable(value = "theme") String theme){
        log.info("theme: "+ theme);
        List<BoardDto.BoardResponseDto> boards = boardService.findAll(theme);
        log.info("boardsList : "+ boards);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(boards,CustomResponseStatus.SUCCESS));
    }

    //특정 게시물 조회
    @GetMapping("/articles/{boardId}")
    public ResponseEntity<ApiResponse<BoardDto.BoardResponseDto>> detailBoard(@PathVariable(value = "boardId") Long boardId){
        BoardDto.BoardResponseDto boardResponseDto = boardService.findBoard(boardId);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(boardResponseDto,CustomResponseStatus.SUCCESS));
    }
    /**
     * 게시글 수정
     */
    //특정 게시물
    @PutMapping("/articles/{boardId}")
    public ResponseEntity<ApiResponse<BoardDto.BoardResponseDto>> updateBoard(@RequestBody BoardDto.BoardRequestDto boardRequestDto, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        BoardDto.BoardResponseDto result = boardService.updateBoard(boardRequestDto, userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(result,CustomResponseStatus.SUCCESS));
    }

    /**
    게시글 삭제
     */
    @DeleteMapping("/articles/{boardId}")
    public ResponseEntity<ApiResponse<String>> delBoard(@RequestBody BoardDto.BoardRequestDto boardRequestDto, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        boardService.delBoard(boardRequestDto, userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS));
    }

    /**
     * 상대방 차단하기
     */
    @PostMapping("/block")
    public ResponseEntity<ApiResponse<String>> blockUser(@RequestBody BoardDto.BlockUserReqDto blockReqDto, Authentication authentication) {
        String username = authentication.getName();
        blockUserService.blockUser(username, blockReqDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS));
    }

    /**
     * 상대방 차단 해제
     */
    @PostMapping("/unblock")
    public ResponseEntity<ApiResponse<String>> unblockUser(@RequestBody BoardDto.UnblockUserReqDto unblockReqDto, Authentication authentication) {
        String username = authentication.getName();
        blockUserService.unblockUser(username, unblockReqDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS));
    }

    /**
     * 핫한 게시물 조회
     */
    @GetMapping("/hot-board")
    public ResponseEntity<ApiResponse<List<BoardDto.BoardResponseDto>>> getHotBoardList() {
        List<BoardDto.BoardResponseDto> hotBoardList = boardGoodService.findHotBoard();
        return ResponseEntity.ok().body(ApiResponse.createSuccess(hotBoardList, CustomResponseStatus.SUCCESS));
    }

    /**
     * Look 랭킹
     */
    @GetMapping("/look/lank")
    public ResponseEntity<ApiResponse<List<BoardDto.BoardResponseDto>>> getLookLank(){
        List<BoardDto.BoardResponseDto> lookLankList = boardGoodService.lookLank();
        return ResponseEntity.ok().body(ApiResponse.createSuccess(lookLankList,CustomResponseStatus.SUCCESS));
    }
}
