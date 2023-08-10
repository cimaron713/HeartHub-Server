package com.umc_spring.Heart_Hub.board.service.community;

import com.umc_spring.Heart_Hub.board.dto.community.BoardDto;
import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.board.model.community.BoardGood;
import com.umc_spring.Heart_Hub.board.repository.community.BoardGoodRepository;
import com.umc_spring.Heart_Hub.board.repository.community.BoardRepository;
import com.umc_spring.Heart_Hub.user.model.User;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardGoodService {
    private final BoardRepository boardRepository;
    private final BoardGoodRepository boardGoodRepository;
    private final UserRepository userRepository;


    //좋아요 생성
    public void goodRegister(String userName, Long boardId){
        User user = userRepository.findByUsername(userName);
        Board board = boardRepository.findById(boardId).orElseThrow();

        //좋아요 안누른 게시글만 좋아요 누르게 허용
        if(boardGoodRepository.findByUserAndBoard(user, board).isEmpty()){
            BoardGood boardGood = BoardGood.builder()
                    .user(user)
                    .board(board)
                    .build();
            boardGoodRepository.save(boardGood);
        }
        //좋아요 누른 게시글은 좋아요가 사라지게.
        else{
            BoardGood boardGood = boardGoodRepository.findByUserAndBoard(user,board).orElseThrow();
            boardGoodRepository.delete(boardGood);
        }
    }

    public int goodCount(Long boardId){
        Board board = boardRepository.findById(boardId).orElseThrow();
        List<BoardGood> boards = boardGoodRepository.findAllByBoard(board);
        return boards.size();
    }
    //좋아요 취소

    /**
     * 좋아요를 기준으로 상위 3개의 게시물 반환
     */
    public List<BoardDto.BoardResponseDto> findHotBoard() {
        List<Board> hotBoardList = boardGoodRepository.findTop3ByBoard();

        return hotBoardList.stream().map(BoardDto.BoardResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 좋아요 기준으로 Look 게시물 상위 3개
     */
    public List<BoardDto.BoardResponseDto> lookLank(){
        LocalDate today = LocalDate.now();
        int dayValue = today.getDayOfWeek().getValue();
        LocalDate startDay = today.minusDays((dayValue+1));
        LocalDate endDay = today.plusDays((7-dayValue));
        List<Board> looks = boardGoodRepository.findTop3ByBoard_Theme(startDay,endDay);
        List<BoardDto.BoardResponseDto> result = looks.stream().map(BoardDto.BoardResponseDto::new).collect(Collectors.toList());
        return result;
    }
}
