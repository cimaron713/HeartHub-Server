package com.umc_spring.Heart_Hub.board.controller.community;

import com.umc_spring.Heart_Hub.board.dto.community.BoardDto;
import com.umc_spring.Heart_Hub.board.dto.community.CommentDto;
import com.umc_spring.Heart_Hub.board.model.community.Comment;
import com.umc_spring.Heart_Hub.board.service.community.BoardService;
import com.umc_spring.Heart_Hub.board.service.community.CommentService;
import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class CommentCotroller {
    private CommentService commentService;
    private BoardService boardService;
    /*
    해당 게시글의 댓글 조회
     */
    @GetMapping("/board/{theme}/{boardid}/comments")
    public ResponseEntity<ApiResponse<List<CommentDto.Response>>> getComments(@PathVariable String theme, @PathVariable Long boardId) {
        BoardDto.BoardResponseDto board = boardService.findBoard(theme,boardId);
        List<CommentDto.Response> comments = commentService.findComments(board);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(comments,"get comments success"));
    }
    /*
    해당 게시글의 댓글 등록
     */
    @PostMapping("/board/{theme}/{boardid}/comments")
    public ResponseEntity<ApiResponse<Long>> getComments(@RequestBody CommentDto.Request request, @PathVariable Long boardId,
                                                         Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long id = commentService.createComment(boardId, request, userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(id,"get comments success"));
    }
}
