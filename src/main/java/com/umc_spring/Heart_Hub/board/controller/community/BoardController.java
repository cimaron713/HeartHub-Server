package com.umc_spring.Heart_Hub.board.controller.community;

import com.umc_spring.Heart_Hub.board.dto.community.BoardDto;
import com.umc_spring.Heart_Hub.board.dto.community.BoardImageUploadDto;
import com.umc_spring.Heart_Hub.board.service.community.BlockUserService;
import com.umc_spring.Heart_Hub.board.service.community.BoardGoodService;
import com.umc_spring.Heart_Hub.board.service.community.BoardService;
import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import com.umc_spring.Heart_Hub.constant.enums.CustomResponseStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@RequestMapping("/api/user/board")
public class BoardController {
    private BoardService boardService;
    private BlockUserService blockUserService;
    private BoardGoodService boardGoodService;
    //게시글 작성

    //게시글 작성
    @PostMapping("/articles/write")
    public ResponseEntity<ApiResponse<Long>> boardWrite(@RequestBody BoardDto.BoardRequestDto params,
                                                        @RequestPart("files")BoardImageUploadDto boardImageUploadDto,
                                                        Authentication authentication){
        log.info("boardRequestDto: "+ params.getContent());
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        Long boardId = boardService.boardRegister(params, userDetails.getUsername(), boardImageUploadDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(boardId, CustomResponseStatus.SUCCESS));
    }

    /*
    게시글 조회
     */
    //전체
    @GetMapping("/{theme}/articles")
    public ResponseEntity<ApiResponse<List<BoardDto.BoardResponseDto>>> articleList(@PathVariable(value = "theme") String theme){
        List<BoardDto.BoardResponseDto> boards = boardService.findAll(theme);
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
    public ResponseEntity<ApiResponse<Long>> updateBoard(@PathVariable(value = "boardId") Long boardId,
                                                         @RequestBody BoardDto.BoardRequestDto boardRequestDto){
        Long id = boardService.updateBoard(boardId,boardRequestDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(id,CustomResponseStatus.SUCCESS));
    }

    /**
    게시글 삭제
     */
    @DeleteMapping("/articles/{boardId}")
    public ResponseEntity<ApiResponse<String>> delBoard(@PathVariable(value = "boardId") Long boardId){
        boardService.delBoard(boardId);
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
