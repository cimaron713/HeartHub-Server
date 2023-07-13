package com.umc_spring.Heart_Hub.board.repository.mission;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc_spring.Heart_Hub.board.model.mission.Mission;
import com.umc_spring.Heart_Hub.config.mission.QMission;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MissionRepositoryImpl implements MissionRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Mission> getMissions(Mission mission, Long userId) {
        QMission m = QMission.mission;

        List<Mission> missions = jpaQueryFactory
                .selectFrom(m)
                .where(m.user.userId.eq(userId))
                .and(m.checkStatus.eq("0"))
                .fetch();
    }
}
