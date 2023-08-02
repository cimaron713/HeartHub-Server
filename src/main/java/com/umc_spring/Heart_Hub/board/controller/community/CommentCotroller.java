package com.umc_spring.Heart_Hub.board.controller.community;

import com.umc_spring.Heart_Hub.board.dto.community.BoardDto;
import com.umc_spring.Heart_Hub.board.dto.community.CommentDto;
import com.umc_spring.Heart_Hub.board.model.community.Comment;
import com.umc_spring.Heart_Hub.board.service.community.BoardService;
import com.umc_spring.Heart_Hub.board.service.community.CommentService;
import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import com.umc_spring.Heart_Hub.constant.enums.CustomResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentCotroller {
    private final CommentService commentService;
    private final BoardService boardService;
    /*
    해당 게시글의 댓글 조회
     */
    @GetMapping("/api/user/board/{boardid}/comments")
    public ResponseEntity<ApiResponse<List<CommentDto.Response>>> getComments(@PathVariable(value = "boardId") Long boardId,
                                                                              Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        BoardDto.BoardResponseDto board = boardService.findBoard(boardId);
        List<CommentDto.Response> comments = commentService.findComments(board, userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(comments, CustomResponseStatus.SUCCESS));
    }
    /*
    해당 게시글의 댓글 등록
     */
    @PostMapping("/api/user/board/comments")
    public ResponseEntity<ApiResponse<String>> getComments(@RequestBody CommentDto.Request request,
                                                         Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long id = commentService.createComment(request, userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(id.toString(),CustomResponseStatus.SUCCESS));
    }

    @DeleteMapping("/api/user/board")
    public ResponseEntity<ApiResponse<CommentDto.DeleteResponse>> deleteComment(CommentDto.DeleteRequest deleteRequest,
                                                             Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        CommentDto.DeleteResponse deleteResponse = commentService.deleteComment(deleteRequest, userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(deleteResponse, CustomResponseStatus.SUCCESS));
    }
}
