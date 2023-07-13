package com.umc_spring.Heart_Hub.coupleBoard.model;

import com.umc_spring.Heart_Hub.constant.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoupleBoardImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Column(name = "coupleImgUrl", nullable = false)
    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "postId")
    private CoupleBoard coupleBoard;
}
