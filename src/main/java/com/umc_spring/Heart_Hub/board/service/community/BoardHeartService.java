package com.umc_spring.Heart_Hub.board.service.community;

import com.umc_spring.Heart_Hub.board.dto.community.BoardHeartDto;
import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.board.model.community.BoardHeart;
import com.umc_spring.Heart_Hub.board.repository.community.BoardHeartRepository;
import com.umc_spring.Heart_Hub.board.repository.community.BoardRepository;
import com.umc_spring.Heart_Hub.user.model.User;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class BoardHeartService {
    private BoardRepository boardRepository;
    private UserRepository userRepository;
    private BoardHeartRepository boardHeartRepository;
    public void heartRegister(@RequestBody BoardHeartDto.Request params, String userName){
        User user = userRepository.findByUsername(userName);
        Board board = boardRepository.findById(params.getBoardId()).orElseThrow();
        if(boardHeartRepository.findByUserAndBoard(user,board) == null){
            BoardHeart boardHeart = BoardHeart.builder()
                    .board(board)
                    .user(user)
                    .build();
            boardHeartRepository.save(boardHeart);
        }
        else{
            BoardHeart boardHeart = boardHeartRepository.findByUserAndBoard(user,board).orElseThrow();
            boardHeartRepository.delete(boardHeart);
        }
    }
}
