package com.umc_spring.Heart_Hub.board.model.mission;

import com.umc_spring.Heart_Hub.user.model.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long missionId;

    private String content;

    // 0(Fail), 1(Success) , 미션의 성공 상태정보
    @Column(columnDefinition = "VARCHAR(1) DEFAULT '0'")
    private String checkStatus;

    // 0 (Not Delete), 1 (Delete), 미션의 삭제 상태정보
    @Column(columnDefinition = "VARCHAR(1) DEFAULT '0'")
    private String deleteStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Builder
    public Mission(String content, User user) {
        this.content = content;
        this.user = user;
    }
}
