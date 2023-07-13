package com.umc_spring.Heart_Hub.board.dto.mission;

import com.umc_spring.Heart_Hub.board.model.mission.Mission;
import com.umc_spring.Heart_Hub.user.model.User;
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
    public static class MissionRequestDto{
        private String content;

        public Mission toEntity(User user) {
            return Mission.builder()
                    .content(content)
                    .user(user)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class RandomMissionRespDto {
        private List<MissionDto> randomMissions;
        private Long userId;

        @Builder
        public RandomMissionRespDto(List<Mission> missions, User user) {
            this.randomMissions = convertToDto(missions);
            this.userId = user.getUserId();
        }

        private List<MissionDto> convertToDto(List<Mission> missions) {
            ModelMapper modelMapper = new ModelMapper();
            return missions.stream()
                    .map(mission -> modelMapper.map(mission, MissionDto.class))
                    .collect(Collectors.toList());
        }
    }
}
