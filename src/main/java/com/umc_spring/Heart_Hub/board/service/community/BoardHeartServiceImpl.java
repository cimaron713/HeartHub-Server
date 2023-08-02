package com.umc_spring.Heart_Hub.board.service.community;

import com.umc_spring.Heart_Hub.board.dto.community.BoardDto;
import com.umc_spring.Heart_Hub.board.dto.community.BoardHeartDto;
import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.board.model.community.BoardHeart;
import com.umc_spring.Heart_Hub.board.repository.community.BoardHeartRepository;
import com.umc_spring.Heart_Hub.board.repository.community.BoardRepository;
import com.umc_spring.Heart_Hub.coupleBoard.dto.CoupleBoardDto;
import com.umc_spring.Heart_Hub.user.model.User;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class BoardHeartServiceImpl implements BoardHeartService{
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardHeartRepository boardHeartRepository;

    /**
     * 스크랩 등록
     * @param params
     * @param userName
     */
    @Override
    public void heartRegister(BoardHeartDto.Request params, String userName){
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

    @Override
    public List<BoardDto.BoardResponseDto> getHeartBoards(String username) {
        return getHeartBoardsByUsername(username);
    }

    @Override
    public List<BoardDto.BoardResponseDto> getHeartMateBoards(String username) {
        User mate = userRepository.findByUsername(username);

        return getHeartBoardsByUsername(mate.getUsername());
    }

    @Override
    public List<BoardDto.BoardResponseDto> getHeartBoardsByUsername(String username) {
        User user = userRepository.findByUsername(username);
        List<BoardHeart> boardHearts = boardHeartRepository.findByUser(user);

        return boardHearts.stream()
                .sorted(Comparator.comparing((BoardHeart boardHeart) -> boardHeart.getBoard().getCreatedDate()).reversed())
                .map(boardHeart -> new BoardDto.BoardResponseDto(boardHeart.getBoard()))
                .collect(Collectors.toList());
    }
}

