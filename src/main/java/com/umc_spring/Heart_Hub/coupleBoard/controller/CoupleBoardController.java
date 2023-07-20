package com.umc_spring.Heart_Hub.coupleBoard.controller;

import com.umc_spring.Heart_Hub.board.repository.community.BoardHeartRepository;
import com.umc_spring.Heart_Hub.board.service.community.BoardHeartService;
import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import com.umc_spring.Heart_Hub.coupleBoard.dto.CoupleBoardDto;
import com.umc_spring.Heart_Hub.coupleBoard.dto.CoupleBoardImageUploadDto;
import com.umc_spring.Heart_Hub.coupleBoard.service.CoupleBoardService;
import com.umc_spring.Heart_Hub.user.model.User;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import lombok.NoArgsConstructor;
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
    private final BoardHeartService boardHeartService;
    private final UserRepository userRepository;

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
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        List<CoupleBoardDto.Response> boardList = coupleBoardService.searchBoardList(createAt, username);
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

    /**
     * 자신이 스크랩한 게시물 조회
     */
    @GetMapping("/scrap-board")
    public ResponseEntity<List<CoupleBoardDto.ScrapResponse>> scrapBoard(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<CoupleBoardDto.ScrapResponse> scrapBoardList = boardHeartService.getHeartBoards(userDetails.getUsername());

        return ResponseEntity.ok(scrapBoardList);
    }

    /**
     * 연동한 계정이 스크랩한 게시물 조회
     */
    @GetMapping("/scrap-mate-board")
    public ResponseEntity<List<CoupleBoardDto.ScrapResponse>> scrapMateBoard(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<CoupleBoardDto.ScrapResponse> scrapBoardList = boardHeartService.getHeartMateBoards(userDetails.getUsername());

        return ResponseEntity.ok(scrapBoardList);
    }

}
