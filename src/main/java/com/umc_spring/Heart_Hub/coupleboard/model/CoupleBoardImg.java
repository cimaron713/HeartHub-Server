package com.umc_spring.Heart_Hub.coupleboard.model;

import com.umc_spring.Heart_Hub.constant.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CoupleBoardImg extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    private String imageUrl;

    @Column(nullable = false, length = 1)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUPLE_BOARD_ID")
    private CoupleBoard coupleBoard;
}
