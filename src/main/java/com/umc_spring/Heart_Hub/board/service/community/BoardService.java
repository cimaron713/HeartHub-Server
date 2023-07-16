package com.umc_spring.Heart_Hub.board.service.community;

import com.umc_spring.Heart_Hub.board.dto.community.BoardDto;
import com.umc_spring.Heart_Hub.board.model.community.BlockedList;
import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.board.repository.community.BlockedListRepository;
import com.umc_spring.Heart_Hub.board.repository.community.BoardRepository;
import com.umc_spring.Heart_Hub.user.model.User;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BlockedListRepository blockedListRepository;
    //등록
    @Transactional
    public Long boardRegister(BoardDto.BoardRequestDto params, String userName){
        User user = userRepository.findByUsername(userName);
        Board boardRegister = Board.builder()
                .user(user)
                .content(params.getContent())
                .build();
        boardRepository.save(boardRegister);
        Long boardId = boardRegister.getBoardId();
        return boardId;
    }

    /*
     게시글 리스트 조회
     */
    @Transactional
    public List<BoardDto.BoardResponseDto> findAll() {
        String username = getLoginUsername();
        User user = userRepository.findByUsername(username);
        //로그인한 사용자가 차단한 사용자들의 목록 가져오기
        List<User> blockedUsers = blockedListRepository.findAllByBlocker(user).stream()
                .map(BlockedList::getBlockedUser)
                .collect(Collectors.toList());

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        List<Board> list = boardRepository.findAll(sort);
        List<BoardDto.BoardResponseDto> responseList = list.stream()
                .filter(board -> !blockedUsers.contains(board.getUser()))
                .map(m -> new BoardDto.BoardResponseDto(m)).toList();
        return responseList;
    }

    @Transactional
    public BoardDto.BoardResponseDto findBoard(final Long id){
        Board findBoard = boardRepository.findById(id).get();
        BoardDto.BoardResponseDto boardResponseDto = new BoardDto.BoardResponseDto(findBoard);
        return boardResponseDto;
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
    public String getLoginUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
