package com.umc_spring.Heart_Hub.coupleBoard.controller;

import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import com.umc_spring.Heart_Hub.coupleBoard.dto.CoupleBoardDto;
import com.umc_spring.Heart_Hub.coupleBoard.dto.CoupleBoardImageUploadDto;
import com.umc_spring.Heart_Hub.coupleBoard.service.CoupleBoardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.MediaType.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CoupleBoardController {

    private static final Logger logger = LoggerFactory.getLogger(CoupleBoardController.class);

    private final CoupleBoardService coupleBoardService;

    /**
     * 게시물 작성
     */
    @PostMapping(value = "/couple-board/write",
            consumes = {APPLICATION_JSON_VALUE, MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse<String>> createBoard(@RequestPart CoupleBoardDto.Request requestDto,
                                                           @RequestPart("files") CoupleBoardImageUploadDto boardImageUploadDto,
                                                           Authentication authentication) {

        logger.info("boardImageDTO is {}", boardImageUploadDto);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long postId = coupleBoardService.saveBoard(requestDto, boardImageUploadDto, userDetails.getUsername());

        return ResponseEntity.ok(ApiResponse.createSuccess(postId.toString(), "Create Success!"));
    }

    /**
     * 게시물 조회 (날짜에 따른)
     */
    @GetMapping("/couple-board/{createAt}")
    public ResponseEntity<List<CoupleBoardDto.Response>> getBoardsByDate(@PathVariable LocalDate createAt, Authentication authentication) {
        List<CoupleBoardDto.Response> boardList = coupleBoardService.searchBoardList(createAt);
        Collections.sort(boardList, Comparator.comparing(CoupleBoardDto.Response::getCreateAt));

        return ResponseEntity.ok(boardList);
    }

    /**
     * 게시물 수정
     */
    @PutMapping("/couple-board/{postId}/update")
    public ResponseEntity<ApiResponse<String>> updateBoard(@PathVariable Long postId, @RequestBody CoupleBoardDto.Request requestDto,
                                                           Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        CoupleBoardDto.Response result = coupleBoardService.detailBoard(postId);
        if(!Objects.equals(result.getUserName(), userDetails.getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.createError(HttpStatus.FORBIDDEN, "You are not allowed to update this board"));
        }

        coupleBoardService.updateBoard(postId, requestDto);

        return ResponseEntity.ok(ApiResponse.createSuccess(postId.toString(), "Update Success!"));
    }

    /**
     * 게시물 삭제
     */
    @DeleteMapping("/couple-board/{postId}/delete")
    public ResponseEntity<ApiResponse<String>> deleteBoard(@PathVariable Long postId, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        CoupleBoardDto.Response result = coupleBoardService.detailBoard(postId);
        if(!Objects.equals(result.getUserName(), userDetails.getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.createError(HttpStatus.FORBIDDEN, "You are not allowed to delete this board"));
        }

        coupleBoardService.deleteBoard(postId);

        return ResponseEntity.ok(ApiResponse.createSuccess(postId.toString(), "Delete Success!"));
    }

}
