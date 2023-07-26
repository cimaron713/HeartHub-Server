package com.umc_spring.Heart_Hub.board.model.community;

import com.umc_spring.Heart_Hub.board.dto.community.BoardDto;
import com.umc_spring.Heart_Hub.constant.entity.BaseEntity;
import com.umc_spring.Heart_Hub.user.model.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    private String goodStatus;

    @Column(nullable = false)
    private String heartStatus;

    @Column(nullable = false, length = 1)
    private String theme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "board", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private List<Comment> comments;

    //이미지
    @OneToMany(mappedBy = "board", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<BoardImg> community = new ArrayList<>();

    @Builder
    public Board(String theme, String content, User user){
        this.theme = theme;
        this.content = content;
        this.user = user;
        this.goodStatus = "T";
        this.heartStatus = "T";
    }
    public void update(String content){
        this.content = content;
    }
    public void deleteGoodStatus(){
        this.goodStatus="F";
    }
    public void deleteHeartStatus(){
        this.heartStatus="F";
    }
}
