package com.umc_spring.Heart_Hub.coupleboard.model;

import com.umc_spring.Heart_Hub.constant.entity.BaseEntity;
import com.umc_spring.Heart_Hub.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoupleBoard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = false, length = 1)
    private String status;

    @OneToMany(mappedBy = "CoupleBoard", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<CoupleBoardImage> boardImages = new ArrayList<>();

//    public CoupleBoard(String content) {
//        this.content = content;
//    }

    public void update(String content) {
        this.content = content;
    }

    public void delete() {
        this.status = String.valueOf('N');
    }

//    public void saveImages(ImageFile images) {
//        this.boardImages.add(images);
////        if(images.getCoupleBoard() != this) {
////            images.setCoupleBoard(this);
////        }
//    }

}
