package com.umc_spring.Heart_Hub.board.repository.mission;

import com.umc_spring.Heart_Hub.board.model.mission.Mission;

import java.util.List;

public interface MissionRepositoryCustom {

    List<Mission> getMissions(Mission mission, Long userId);
}
