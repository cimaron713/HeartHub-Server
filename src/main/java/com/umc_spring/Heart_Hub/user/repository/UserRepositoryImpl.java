package com.umc_spring.Heart_Hub.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc_spring.Heart_Hub.user.model.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long getUserIdByUserName(String username) {
        QUser user = QUser.user;

        Long userId = jpaQueryFactory
                .select(user.userId)
                .from(user)
                .where(user.username.eq(username))
                .fetchOne();

        return userId;
    }
}
