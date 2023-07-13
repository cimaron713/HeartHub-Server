package com.umc_spring.Heart_Hub.board.controller.community;

import com.umc_spring.Heart_Hub.board.dto.community.BoardRequestDto;
import com.umc_spring.Heart_Hub.board.dto.community.BoardResponseDto;
import com.umc_spring.Heart_Hub.board.service.community.BoardService;
import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private BoardService boardService;
    //게시글 작성
    //작성화면

    //게시글 작성
    @PostMapping("/articles/write")
    public ResponseEntity<ApiResponse<Long>> boardWrite(@RequestBody final BoardRequestDto params){
        Long boardId = boardService.boardRegister(params);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(boardId,"Register board success"));
    }

    /*
    게시글 조회
     */
    //전체
    @GetMapping("/articles")
    public ResponseEntity<ApiResponse<List<BoardResponseDto>>> articleList(){
        List<BoardResponseDto> boards = boardService.findAll();
        return ResponseEntity.ok().body(ApiResponse.createSuccess(boards,"Search total boards success"));
    }

    //특정 게시물
    @GetMapping("/articles/{boardId}")
    public ResponseEntity<ApiResponse<BoardResponseDto>> detailBoard(@PathVariable Long boardId){
        BoardResponseDto boardResponseDto = boardService.findBoard(boardId);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(boardResponseDto,"Find board success"));
    }

    /*
    게시글 삭제
     */

    //게시글 삭제
    @DeleteMapping("/articles/{boardId}")
    public ResponseEntity<ApiResponse<String>> delBoard(@PathVariable Long boardId){
        boardService.delBoard(boardId);
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent("Delete board success"));
    }
}
