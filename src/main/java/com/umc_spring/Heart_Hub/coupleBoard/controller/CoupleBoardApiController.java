package com.umc_spring.Heart_Hub.coupleBoard.controller;

import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import com.umc_spring.Heart_Hub.coupleBoard.dto.BoardDto;
import com.umc_spring.Heart_Hub.coupleBoard.dto.BoardImageUploadDto;
import com.umc_spring.Heart_Hub.coupleBoard.service.CoupleBoardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CoupleBoardApiController {

    private static final Logger logger = LoggerFactory.getLogger(CoupleBoardApiController.class);
    private final CoupleBoardService coupleBoardService;

    /**
     * 게시물 작성
     */
    @PostMapping(value = "/board",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse<String>> createBoard(@RequestPart BoardDto.Request requestDto,
                                                           @RequestPart("files") BoardImageUploadDto boardImageUploadDto,
                                                           Authentication authentication) {

        logger.info("boardImageDTO is {}", boardImageUploadDto);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long postId = coupleBoardService.saveBoard(requestDto, boardImageUploadDto, userDetails.getUsername());

        // return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent("Create Success!"));
        return ResponseEntity.ok(ApiResponse.createSuccess(postId.toString(), "Create Success!"));
    }

    /**
     * 게시물 조회 (날짜에 따른)
     */
    @GetMapping


    /**
     * 게시물 수정
     */
    @PutMapping("/board/{postId}")
    public ResponseEntity<ApiResponse<String>> updateBoard(@PathVariable Long postId, BoardDto.Request responseDto) {
        coupleBoardService.updateBoard(postId, responseDto);

        return ResponseEntity.ok(ApiResponse.createSuccess(postId.toString(), "Create Success!"));
    }

    @GetMapping("/{postId}/delete")
    public ResponseEntity<ApiResponse<String>> deleteBoard(@PathVariable Long postId, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        BoardDto.Response result = coupleBoardService.detailBoard(postId);
        if(!Objects.equals(result.getUserName(), userDetails.getUsername())) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, "/")
                    .build();
        }

        coupleBoardService.deleteBoard(postId);

        return ResponseEntity.ok(ApiResponse.createSuccess(postId.toString(), "Create Success!"));
    }
}
