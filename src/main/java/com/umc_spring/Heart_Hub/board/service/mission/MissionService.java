package com.umc_spring.Heart_Hub.board.service.mission;

import com.umc_spring.Heart_Hub.board.dto.mission.MissionDto;

public interface MissionService {

    public void addMissionToUser(MissionDto.MissionRequestDto missionRequestDto);
}
