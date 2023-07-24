package com.umc_spring.Heart_Hub.board.controller.community;

import com.umc_spring.Heart_Hub.board.dto.community.BoardDto;
import com.umc_spring.Heart_Hub.board.dto.community.BoardImageUploadDto;
import com.umc_spring.Heart_Hub.board.service.community.BlockUserService;
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
    //게시글 작성

    //게시글 작성
    @PostMapping("/articles/write")
    public ResponseEntity<ApiResponse<Long>> boardWrite(@RequestBody BoardDto.BoardRequestDto params,
                                                        @RequestPart("files")BoardImageUploadDto boardImageUploadDto,
                                                        Authentication authentication){
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        Long boardId = boardService.boardRegister(params, userDetails.getUsername(), boardImageUploadDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(boardId,"Register board success"));
    }

    /*
    게시글 조회
     */
    //전체
    @GetMapping("/articles")
    public ResponseEntity<ApiResponse<List<BoardDto.BoardResponseDto>>> articleList(){
        List<BoardDto.BoardResponseDto> boards = boardService.findAll();
        return ResponseEntity.ok().body(ApiResponse.createSuccess(boards,"Search total boards success"));
    }

    //특정 게시물
    @GetMapping("/articles/{boardId}")
    public ResponseEntity<ApiResponse<BoardDto.BoardResponseDto>> detailBoard(@PathVariable Long boardId){
        BoardDto.BoardResponseDto boardResponseDto = boardService.findBoard(boardId);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(boardResponseDto,"Find board success"));
    }
    /**
     * 게시글 수정
     */
    //특정 게시물
    @PutMapping("/articles/{boardId}")
    public ResponseEntity<ApiResponse<Long>> updateBoard(@PathVariable Long boardId,
                                                         @RequestBody BoardDto.BoardRequestDto boardRequestDto){
        Long id = boardService.updateBoard(boardId,boardRequestDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(id,"Update board success"));
    }

    /**
    게시글 삭제
     */
    @DeleteMapping("/articles/{boardId}")
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
}
