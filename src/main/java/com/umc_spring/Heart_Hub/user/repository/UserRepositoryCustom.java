package com.umc_spring.Heart_Hub.user.repository;

import com.umc_spring.Heart_Hub.user.model.User;

public interface UserRepositoryCustom {
    Long getUserIdByUserName(String username);
    User getDdayByUserName(String username);
}
