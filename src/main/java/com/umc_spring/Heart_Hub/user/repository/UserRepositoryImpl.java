package com.umc_spring.Heart_Hub.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc_spring.Heart_Hub.user.model.QUser;
import com.umc_spring.Heart_Hub.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import static com.umc_spring.Heart_Hub.user.model.QUser.user1;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long getUserIdByUserName(String username) {

        Long userId = jpaQueryFactory
                .select(user1.userId)
                .from(user1)
                .where(user1.username.eq(username))
                .fetchOne();

        return userId;
    }

    @Override
    public User getDdayByUserName(String username) {

        User findUser = jpaQueryFactory
                .selectFrom(user1)
                .where(user1.username.eq(username))
                .fetchOne();

        return findUser;
    }
}
