package com.umc_spring.Heart_Hub.board.repository.mission;

import com.umc_spring.Heart_Hub.board.model.mission.UserMissionStatus;

import java.util.List;

public interface UMSRepositoryCustom {

    List<UserMissionStatus> getMissions(Long userId);
}
