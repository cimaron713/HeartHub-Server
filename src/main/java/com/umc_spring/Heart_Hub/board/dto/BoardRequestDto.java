package com.umc_spring.Heart_Hub.board.dto;

import com.umc_spring.Heart_Hub.board.model.Board;
import com.umc_spring.Heart_Hub.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardRequestDto {
    private String content;
    private User user;

    public Board toEntity(){
        return Board.builder()
                .content(content)
                .user(user)
                .build();
    }
}
