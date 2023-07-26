package com.umc_spring.Heart_Hub.board.model.community;

import com.umc_spring.Heart_Hub.constant.entity.BaseEntity;
import com.umc_spring.Heart_Hub.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardGood extends BaseEntity {
    @Id
    @JoinColumn
    private Long goodId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @Column(nullable = false, length = 1)
    private String status;

    @Builder
    public BoardGood(User user, Board board){
        this.user = user;
        this.board = board;
        this.status = "T";
    }

    public void deleteStatus(){
        this.status = "F";
    }

}