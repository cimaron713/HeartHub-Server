package com.umc_spring.Heart_Hub.board.model.community;

import com.querydsl.core.types.EntityPath;
import com.umc_spring.Heart_Hub.constant.entity.BaseEntity;
import com.umc_spring.Heart_Hub.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Comment extends BaseEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(length = 200)
    private String content;

    @OneToMany(mappedBy = "comment",cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @Builder.Default
    private List<CommentGood> goods = new ArrayList<>();

    @Column(nullable = false, length = 1)
    private String status;
    @ColumnDefault(value = "0")
    private int count;

    /*
    대댓글
     */
    //답글을 달 댓글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Comment parent;

    //답글 리스트
    @Builder.Default
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> childComment = new ArrayList<>();

    public void Comment(String content) {
        this.content = content;
    }

    public void updateBoard(Board board) {
        this.board = board;
    }

    public void updateParent(Comment comment) {
        this.parent = comment;
    }
    public void updateChild(List<Comment> child){
        this.childComment = child;
    }
    public void updateUser(User user){
        this.user = user;
    }
    public void modifyStatus(String status){
        this.status = status;
    }
}
