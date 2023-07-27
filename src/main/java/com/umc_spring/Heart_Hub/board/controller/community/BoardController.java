package com.umc_spring.Heart_Hub.board.controller.community;

import com.umc_spring.Heart_Hub.board.dto.community.BoardDto;
import com.umc_spring.Heart_Hub.board.dto.community.BoardImageUploadDto;
import com.umc_spring.Heart_Hub.board.service.community.BlockUserService;
import com.umc_spring.Heart_Hub.board.service.community.BoardGoodService;
import com.umc_spring.Heart_Hub.board.service.community.BoardService;
import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private BoardService boardService;
    private BlockUserService blockUserService;
    private BoardGoodService boardGoodService;
    //게시글 작성

    //게시글 작성
    @PostMapping("/{theme}/articles/write")
    public ResponseEntity<ApiResponse<Long>> boardWrite(@PathVariable String theme, @RequestBody BoardDto.BoardRequestDto params,
                                                        @RequestPart("files")BoardImageUploadDto boardImageUploadDto,
                                                        Authentication authentication){
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        Long boardId = boardService.boardRegister(theme, params, userDetails.getUsername(), boardImageUploadDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(boardId,"Register board success"));
    }

    /*
    게시글 조회
     */
    //전체
    @GetMapping("/{theme}/articles")
    public ResponseEntity<ApiResponse<List<BoardDto.BoardResponseDto>>> articleList(@PathVariable String theme){
        List<BoardDto.BoardResponseDto> boards = boardService.findAll(theme);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(boards,"Search total boards success"));
    }

    //특정 게시물
    @GetMapping("/{theme}/articles/{boardId}")
    public ResponseEntity<ApiResponse<BoardDto.BoardResponseDto>> detailBoard(@PathVariable String theme, @PathVariable Long boardId){
        BoardDto.BoardResponseDto boardResponseDto = boardService.findBoard(theme,boardId);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(boardResponseDto,"Find board success"));
    }
    /**
     * 게시글 수정
     */
    //특정 게시물
    @PutMapping("/{theme}/articles/{boardId}")
    public ResponseEntity<ApiResponse<Long>> updateBoard(@PathVariable Long boardId,
                                                         @RequestBody BoardDto.BoardRequestDto boardRequestDto){
        Long id = boardService.updateBoard(boardId,boardRequestDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(id,"Update board success"));
    }

    /**
    게시글 삭제
     */
    @DeleteMapping("/{theme}/articles/{boardId}")
    public ResponseEntity<ApiResponse<String>> delBoard(@PathVariable Long boardId){
        boardService.delBoard(boardId);
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent("Delete board success"));
    }

    /**
     * 상대방 차단하기
     */
    @PostMapping("/api/block")
    public ResponseEntity<ApiResponse<String>> blockUser(@RequestBody BoardDto.BlockUserReqDto blockReqDto, Authentication authentication) {
        String username = authentication.getName();
        blockUserService.blockUser(username, blockReqDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent("Block user success"));
    }

    /**
     * 상대방 차단 해제
     */
    @PostMapping("/api/unblock")
    public ResponseEntity<ApiResponse<String>> unblockUser(@RequestBody BoardDto.UnblockUserReqDto unblockReqDto, Authentication authentication) {
        String username = authentication.getName();
        blockUserService.unblockUser(username, unblockReqDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent("Unblock user success"));
    }

    /**
     * 핫한 게시물 조회
     */
    @GetMapping("/api/hot-board")
    public ResponseEntity<ApiResponse<List<BoardDto.BoardResponseDto>>> getHotBoardList() {
        List<BoardDto.BoardResponseDto> hotBoardList = boardGoodService.findHotBoard();
        return ResponseEntity.ok().body(ApiResponse.createSuccess(hotBoardList, "Get Hot Board Success!"));
    }
}
