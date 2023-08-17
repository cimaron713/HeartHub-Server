package com.umc_spring.Heart_Hub.user.repository;

import com.umc_spring.Heart_Hub.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findByEmail(String email);
    User findByUsername(String username);
    User findByUserId(Long userId);
    Optional<User> findByNickname(String nickname);
}
