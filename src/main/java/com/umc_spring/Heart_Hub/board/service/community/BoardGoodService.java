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

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardGoodService {
    private BoardRepository boardRepository;
    private BoardGoodRepository boardGoodRepository;
    private UserRepository userRepository;


    //좋아요 생성
    public void goodRegister(BoardDto.BoardRequestDto boardRequest, String userName){
        User user = userRepository.findByUsername(userName);
        Board board = boardRepository.findById(boardRequest.getBoardId()).orElseThrow();

        //좋아요 안누른 게시글만 좋아요 누르게 허용
        if(boardGoodRepository.findByUserAndBoard(user, board) == null){
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

    public int goodCount(Long id){
        List<BoardGood> boardGoods = boardGoodRepository.countGood(id);
        return boardGoods.size();
    }
    //좋아요 취소
}
