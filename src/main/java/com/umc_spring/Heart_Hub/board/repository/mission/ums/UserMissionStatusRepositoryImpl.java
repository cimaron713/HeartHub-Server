package com.umc_spring.Heart_Hub.board.repository.mission.ums;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc_spring.Heart_Hub.board.model.mission.QUserMissionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserMissionStatusRepositoryImpl implements UMSRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Long> getMissionIds(Long userId) {
        QUserMissionStatus userMissionStatus = QUserMissionStatus.userMissionStatus;

        List<Long> missionIdList = jpaQueryFactory
                .select(userMissionStatus.mission.missionId)
                .from(userMissionStatus)
                .where(userMissionStatus.user.userId.eq(userId)
                        .and(userMissionStatus.checkStatus.eq("0")))
                .fetch();

        return missionIdList;
    }
}
