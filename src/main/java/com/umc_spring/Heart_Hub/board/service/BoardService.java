package com.umc_spring.Heart_Hub.board.service;

import com.umc_spring.Heart_Hub.board.dto.BoardRequestDto;
import com.umc_spring.Heart_Hub.board.dto.BoardResponseDto;
import com.umc_spring.Heart_Hub.board.model.Board;
import com.umc_spring.Heart_Hub.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    //등록

    @Transactional
    public Long save(final BoardRequestDto params) {

        Board entity = boardRepository.save(params.toEntity());
        return entity.getBoardId();
    }
    /*
     게시글 리스트 조회
     */
    public List<BoardResponseDto> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id", "createdDate");
        List<Board> list = boardRepository.findAll(sort);
        return list.stream().map(BoardResponseDto::new).collect(Collectors.toList());
    }

    public BoardResponseDto findBoard(final Long id){
        Board entity = boardRepository.findById(id).get();
        return new BoardResponseDto(entity);
    }

    /*
     게시글 삭제
     */
    public void delBoard(final Long id){
        if (boardRepository.findById(id).isPresent()){
            Board board = boardRepository.findById(id).get();
            boardRepository.delete(board);
        }
    }
}