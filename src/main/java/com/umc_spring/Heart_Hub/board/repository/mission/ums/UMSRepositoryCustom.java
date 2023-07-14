package com.umc_spring.Heart_Hub.board.repository.mission.ums;

import java.util.List;

public interface UMSRepositoryCustom {

    List<Long> getMissionIds(Long userId);
    Long findUMSByMissionIdAndUserId(Long missionId, Long userId);
}
