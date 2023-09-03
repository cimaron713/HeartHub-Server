package com.umc_spring.Heart_Hub.coupleBoard.repository;

import com.umc_spring.Heart_Hub.coupleBoard.model.CoupleBoard;
import com.umc_spring.Heart_Hub.user.model.User;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CoupleBoardRepository extends JpaRepository<CoupleBoard, Long> {

    List<CoupleBoard> findAllByUserAndCreatedDate(User user, LocalDate createAt);
    List<CoupleBoard> findAllByUserAndPostId(User user, Long postId);
}
