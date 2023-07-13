package com.umc_spring.Heart_Hub.board.repository.mission;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc_spring.Heart_Hub.config.mission.Mission;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MissionRepositoryImpl implements MissionRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Mission> getMissions(Mission mission) {
        return null;
    }
}
