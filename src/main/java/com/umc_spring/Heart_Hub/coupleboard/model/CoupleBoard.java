package com.umc_spring.Heart_Hub.coupleboard.model;

import com.umc_spring.Heart_Hub.constant.entity.BaseEntity;
import com.umc_spring.Heart_Hub.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CoupleBoard extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boarId;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(nullable = false)
    private String status;
}
