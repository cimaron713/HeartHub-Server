package com.umc_spring.Heart_Hub.board.model.mission;

import com.umc_spring.Heart_Hub.constant.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mission extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long missionId;

    private String content;

    // 0 (Not Delete), 1 (Delete), 미션의 삭제 상태정보
    private String deleteStatus;

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL)
    private List<UserMissionStatus> userMissionStatusList;

    @Builder
    public Mission(String content) {
        this.content = content;
        this.deleteStatus = "0";
    }

    public void deleteStatusModify() {
        this.deleteStatus = "1";
    }
}
