package com.umc_spring.Heart_Hub.board.service.community;

import com.umc_spring.Heart_Hub.board.dto.community.BoardDto;
import com.umc_spring.Heart_Hub.board.model.community.Board;
import com.umc_spring.Heart_Hub.board.repository.community.BoardRepository;
import com.umc_spring.Heart_Hub.user.model.User;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private UserRepository userRepository;
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
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        List<Board> list = boardRepository.findAll(sort);
        List<BoardDto.BoardResponseDto> responseList = list.stream().map(m -> new BoardDto.BoardResponseDto(m)).toList();
        return responseList;
    }
    @Transactional
    public BoardDto.BoardResponseDto findBoard(Long id){
        Board findBoard = boardRepository.findById(id).orElseThrow();
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
}
