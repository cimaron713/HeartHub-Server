package com.umc_spring.Heart_Hub.board.service.community;

import com.umc_spring.Heart_Hub.board.dto.community.BoardHeartDto;
import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.board.model.community.BoardHeart;
import com.umc_spring.Heart_Hub.board.repository.community.BoardHeartRepository;
import com.umc_spring.Heart_Hub.board.repository.community.BoardRepository;
import com.umc_spring.Heart_Hub.coupleBoard.dto.CoupleBoardDto;
import com.umc_spring.Heart_Hub.user.model.User;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<CoupleBoardDto.ScrapResponse> getHeartBoards(String username) {
        return getHeartBoardsByUsername(username);
    }

    public List<CoupleBoardDto.ScrapResponse> getHeartMateBoards(String username) {
        User mate = userRepository.findByUser(username);
        return getHeartBoardsByUsername(mate.getUsername());
    }

    public List<CoupleBoardDto.ScrapResponse> getHeartBoardsByUsername(String username) {
        User user =  userRepository.findByUsername(username);
        List<BoardHeart> boardHearts = boardHeartRepository.findByUser(user);

        return boardHearts.stream()
                .sorted(Comparator.comparing((BoardHeart boardHeart) -> boardHeart.getBoard().getCreatedDate()).reversed())
                .map(boardHeart -> new CoupleBoardDto.ScrapResponse(boardHeart.getBoard()))
                .collect(Collectors.toList());
    }
}
