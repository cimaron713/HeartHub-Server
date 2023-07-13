package com.umc_spring.Heart_Hub.board.repository.mission;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc_spring.Heart_Hub.board.model.mission.QMission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MissionRepositoryImpl implements MissionRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public String getContentById(Long missionId) {
        QMission mission = QMission.mission;

        String missionContent = jpaQueryFactory
                .select(mission.content)
                .from(mission)
                .where(mission.missionId.eq(missionId))
                .fetchOne();

        return missionContent;
    }
}
