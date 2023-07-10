package com.umc_spring.Heart_Hub.board.controller;

import com.umc_spring.Heart_Hub.board.dto.BoardResponseDto;
import com.umc_spring.Heart_Hub.board.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private BoardService boardService;
    //게시글 작성
    //작성화면

    //게시글 작성
    @PostMapping("/articles/write")
    public Long boardWrite(@RequestBody final BoardRequestDto params){

        return boardService.save(params);
    }

    /*
    게시글 조회
     */
    //전체
    @GetMapping("/articles")
    public List<BoardResponseDto> articleList(){

        return boardService.findAll();
    }

    //특정 게시물
    @GetMapping("/articles/{boardId}")
    public BoardResponseDto detailBoard(@PathVariable Long boardId){

        return boardService.findBoard(boardId);
    }

    /*
    게시글 삭제
     */

    //게시글 삭제
    @DeleteMapping("/articles/{boardId}")
    public void delBoard(@PathVariable Long boardId){
        boardService.delBoard(boardId);
    }
}
