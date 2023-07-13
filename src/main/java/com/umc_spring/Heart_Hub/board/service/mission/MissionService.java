package com.umc_spring.Heart_Hub.board.service.mission;

import com.umc_spring.Heart_Hub.board.dto.mission.MissionDto;

import java.util.List;

public interface MissionService {

    void addMissionToUser(MissionDto.MissionRequestDto missionRequestDto);

    List<MissionDto.RandomMissionRespDto> getMissions();

}
