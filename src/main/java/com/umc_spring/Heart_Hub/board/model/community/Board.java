package com.umc_spring.Heart_Hub.board.model.community;

import com.umc_spring.Heart_Hub.constant.entity.BaseEntity;
import com.umc_spring.Heart_Hub.user.model.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(length = 200)
    private String content;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false, length = 1)
    private String theme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Builder
    public Board(String content, User user){
        this.content = content;
        this.user = user;
    }
}
