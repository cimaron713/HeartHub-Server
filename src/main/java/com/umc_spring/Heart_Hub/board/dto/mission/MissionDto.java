package com.umc_spring.Heart_Hub.board.dto.mission;

import com.umc_spring.Heart_Hub.board.model.mission.Mission;
import com.umc_spring.Heart_Hub.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class MissionDto {

    private Long missionId;
    private String missionContent;

    @Builder
    public MissionDto(Mission mission) {
        this.missionId = mission.getMissionId();
        this.missionContent = mission.getContent();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionRequestDto{
        private String content;

        public Mission toEntity() {
            return Mission.builder()
                    .content(content)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class RandomMissionRespDto {
        private String missionContent;
        private Long userId;

        @Builder
        public RandomMissionRespDto(String missionContent, User user) {
            this.missionContent = missionContent;
            this.userId = user.getUserId();
        }
    }
}
