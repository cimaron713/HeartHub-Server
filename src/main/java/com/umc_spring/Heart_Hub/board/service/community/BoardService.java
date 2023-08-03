package com.umc_spring.Heart_Hub.board.service.community;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.umc_spring.Heart_Hub.board.dto.community.BoardDto;
import com.umc_spring.Heart_Hub.board.dto.community.BoardImageUploadDto;
import com.umc_spring.Heart_Hub.board.model.community.BlockedList;
import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.board.model.community.BoardImg;
import com.umc_spring.Heart_Hub.board.repository.community.BlockedListRepository;
import com.umc_spring.Heart_Hub.board.repository.community.BoardImgRepository;
import com.umc_spring.Heart_Hub.board.repository.community.BoardRepository;
import com.umc_spring.Heart_Hub.constant.enums.CustomResponseStatus;
import com.umc_spring.Heart_Hub.constant.exception.CustomException;
import com.umc_spring.Heart_Hub.user.model.User;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BlockedListRepository blockedListRepository;
    private final BoardImgRepository boardImgRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    /**
     * 게시글 등록
     */
    public Long boardRegister(BoardDto.BoardRequestDto params, String userName, BoardImageUploadDto boardImageUploadDto){
        User user = userRepository.findByUsername(userName);
        if(user == null) {
            throw new CustomException(CustomResponseStatus.USER_NOT_FOUND);
        }
        Board boardRegister = Board.builder()
                .theme(params.getTheme())
                .user(user)
                .content(params.getContent())
                .status("Y")
                .likeCount(0)
                .build();

        boardRepository.save(boardRegister);
        if(boardImageUploadDto.getCommunityFiles() != null ){
            try {
                List<String> fileUrls = upload(boardImageUploadDto.getCommunityFiles(), user.getUsername());

                for (String fileUrl : fileUrls) {
                    BoardImg img = BoardImg.builder()
                            .postImgUrl(fileUrl)
                            .board(boardRegister)
                            .build();

                    boardImgRepository.save(img);
                }
            } catch (IOException e) {
                throw new CustomException(CustomResponseStatus.IMAGE_NOT_UPLOAD);
            } catch (AmazonServiceException e) {
                System.err.println(e.getErrorMessage());
            } catch (SdkClientException e) {
                System.err.println(e.getMessage());
            }
        }

        return boardRegister.getBoardId();
    }

    public List<String> upload(MultipartFile[] multipartFile, String username) throws IOException {
        List<String> fileUrls = new ArrayList<>();

        for(MultipartFile mf : multipartFile) {
            String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("/yyyy-MM-dd HH:mm"));
            String s3FileName = "Community/"+ username + formatDate + mf.getOriginalFilename();

            ObjectMetadata objMeta = new ObjectMetadata();
            objMeta.setContentLength(mf.getInputStream().available());
            amazonS3.putObject(bucket, s3FileName, mf.getInputStream(), objMeta);

            fileUrls.add(amazonS3.getUrl(bucket, s3FileName).toString());
        }

        return fileUrls;
    }

    /**
     게시글 리스트 조회
     */

    public List<BoardDto.BoardResponseDto> findAll(String theme) {
        String username = getLoginUsername();
        log.info("-------------------------------userName: "+username);
        User user = userRepository.findByUsername(username);
        //로그인한 사용자가 차단한 사용자들의 목록 가져오기
        List<User> blockedUsers = blockedListRepository.findAllByBlocker(user).stream()
                .map(BlockedList::getBlockedUser)
                .collect(Collectors.toList());

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        List<Board> list = boardRepository.findAllByTheme(sort,theme);
        List<BoardDto.BoardResponseDto> responseList = new ArrayList<>();
        if (!list.isEmpty()) {
            responseList = list.stream()
                    .filter(board -> !blockedUsers.contains(board.getUser()))
                    .map(BoardDto.BoardResponseDto::new).toList();
        }
        return responseList;

    }

    /**
     * 사용자가 게시한 게시물들 조회하기
     */
    public List<BoardDto.BoardResponseDto> findUserBoards(String userName, String theme){
        List<Board> boardList;
        User user = userRepository.findByUsername(userName);
        if(user == null){
            throw new CustomException(CustomResponseStatus.USER_NOT_FOUND);
        }
        Sort sort = Sort.by(Sort.Direction.DESC,"creatAt");
        boardList = boardRepository.findAllByTheme(sort,theme);
        List<BoardDto.BoardResponseDto> responseList = new ArrayList<>();
        for(Board board : boardList){
            responseList.add(new BoardDto.BoardResponseDto(board));
        }
        return responseList;
    }

    /**
     * 특정 게시물 조회
     */
    @Transactional
    public BoardDto.BoardResponseDto findBoard(Long id){
        Board findBoard = boardRepository.findById(id).orElseThrow();
        BoardDto.BoardResponseDto boardResponseDto = new BoardDto.BoardResponseDto(findBoard);
        return boardResponseDto;
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public Long updateBoard(Long id, BoardDto.BoardRequestDto requestDto){
        Board board = boardRepository.findByBoardIdAndTheme(id, requestDto.getTheme()).orElseThrow(()->new CustomException(CustomResponseStatus.POST_NOT_FOUND));
        board.update(requestDto.getContent());
        return id;
    }

    /**
     게시글 삭제
     */
    @Transactional
    public void delBoard(final Long id){
        if (boardRepository.findById(id).isPresent()){
            Board board = boardRepository.findById(id).get();
            boardRepository.delete(board);
        }
    }

    /**
     * 현재 로그인한 유저의 이름 가져오기
     */
    @Transactional
    public String getLoginUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    /**
     * 사용자가 작성한 게시물 모두 삭제하기
     */
    @Transactional
    public void delAllBoard(User user) {
        List<Board> boardList = boardRepository.findAllByUser(user);
        boardRepository.deleteAll(boardList);
    }
}
