package com.umc_spring.Heart_Hub.board.model.community;

import com.umc_spring.Heart_Hub.board.dto.community.BoardDto;
import com.umc_spring.Heart_Hub.constant.entity.BaseEntity;
import com.umc_spring.Heart_Hub.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(length = 200)
    private String content;

    @Column(nullable = false, length = 1)
    private String status;

    /**
     * D: Daily, T: Date, L: Look
     */
    @Column(nullable = false, length = 1)
    private String theme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "board", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<Comment> comments;

    //이미지
    @OneToMany(mappedBy = "board", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @Builder.Default
    private List<BoardImg> community = new ArrayList<>();

    public void update(String content){
        this.content = content;
    }
    public void delete() {
        this.status = String.valueOf('N');
    }

}
