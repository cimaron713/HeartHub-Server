package com.umc_spring.Heart_Hub.board.service.community;

import com.umc_spring.Heart_Hub.board.dto.community.BoardDto;
import com.umc_spring.Heart_Hub.board.dto.community.BoardImageUploadDto;
import com.umc_spring.Heart_Hub.board.model.community.BlockedList;
import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.board.model.community.BoardImg;
import com.umc_spring.Heart_Hub.board.repository.community.BlockedListRepository;
import com.umc_spring.Heart_Hub.board.repository.community.BoardImgRepository;
import com.umc_spring.Heart_Hub.board.repository.community.BoardRepository;
import com.umc_spring.Heart_Hub.constant.enums.ErrorCode;
import com.umc_spring.Heart_Hub.constant.exception.CustomException;
import com.umc_spring.Heart_Hub.user.model.User;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BlockedListRepository blockedListRepository;
    private final BoardImgRepository boardImgRepository;

    @Value("${file.communityImgPath}")
    private String communityImgFolder;
    //등록
    @Transactional
    public Long boardRegister(String theme, BoardDto.BoardRequestDto params, String userName, BoardImageUploadDto boardImageUploadDto){
        User user = userRepository.findByUsername(userName);
        if(user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        Board boardRegister = Board.builder()
                .theme(theme)
                .user(user)
                .content(params.getContent())
                .theme(params.getTheme())
                .build();
        boardRepository.save(boardRegister);
        if(boardImageUploadDto.getCommunityFiles() != null && !boardImageUploadDto.getCommunityFiles().isEmpty()){
            for(MultipartFile file : boardImageUploadDto.getCommunityFiles()){
                UUID uuid = UUID.randomUUID();
                String communityImgFileName = uuid+"_"+file.getOriginalFilename();
                File imgPath = new File(communityImgFolder+communityImgFileName);
                try{
                    file.transferTo(imgPath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                BoardImg boardImg = BoardImg.builder()
                        .postImgUrl("/boardImgs/"+communityImgFileName)
                        .board(boardRegister)
                        .build();
                boardImgRepository.save(boardImg);
            }
        }

        return boardRegister.getBoardId();
    }

    /*
     게시글 리스트 조회
     */
    @Transactional
    public List<BoardDto.BoardResponseDto> findAll(String theme) {
        String username = getLoginUsername();
        User user = userRepository.findByUsername(username);
        //로그인한 사용자가 차단한 사용자들의 목록 가져오기
        List<User> blockedUsers = blockedListRepository.findAllByBlocker(user).stream()
                .map(BlockedList::getBlockedUser)
                .collect(Collectors.toList());

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        List<Board> list = boardRepository.findAllByTheme(sort,theme);
        List<BoardDto.BoardResponseDto> responseList = list.stream()
                .filter(board -> !blockedUsers.contains(board.getUser()))
                .map(m -> new BoardDto.BoardResponseDto(m)).toList();
        return responseList;
    }

    /**
     * 특정 게시물 조회
     */
    @Transactional
    public BoardDto.BoardResponseDto findBoard(String theme, Long id){
        Board findBoard = boardRepository.findByBoardIdAndTheme(id,theme).orElseThrow();
        BoardDto.BoardResponseDto boardResponseDto = new BoardDto.BoardResponseDto(findBoard);
        return boardResponseDto;
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public Long updateBoard(Long id, BoardDto.BoardRequestDto requestDto){
        Board board = boardRepository.findByBoardIdAndTheme(id, requestDto.getTheme()).orElseThrow(()->new CustomException(ErrorCode.POST_NOT_FOUND));
        board.update(requestDto.getContent());
        return id;
    }

    /*
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
