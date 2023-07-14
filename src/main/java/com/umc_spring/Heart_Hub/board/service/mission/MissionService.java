package com.umc_spring.Heart_Hub.board.service.mission;

import com.umc_spring.Heart_Hub.board.dto.mission.MissionDto;

import java.util.List;

public interface MissionService {

    void addMissionToUser(MissionDto.MissionRequestDto missionRequestDto);

    List<MissionDto.RandomMissionRespDto> getMissions();

    /**
     * checkStatus 상태 변경 api
     */
    Long checkStatusModify(Long missionId, String username);

    /**
     * deleteStatus 상태 변경 api (mission 삭제)
     */
    void deleteMission(Long missionId);

}
