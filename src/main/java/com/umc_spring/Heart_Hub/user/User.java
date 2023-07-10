package com.umc_spring.Heart_Hub.user;

import com.umc_spring.Heart_Hub.constant.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 45)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 1)
    private String gender;

    @Column(nullable = false, length = 45)
    private String email;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private String userImgUrl;

    @Column(nullable = false, length = 45)
    private String nickname;

    @Column(nullable = false, length = 1)
    private String marketingStatus;

    private LocalDateTime birth;

    @Column(nullable = false, length = 1)
    private String status;

    private Long mate;
}