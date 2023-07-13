package com.umc_spring.Heart_Hub.coupleBoard.repository;

import com.umc_spring.Heart_Hub.coupleBoard.model.CoupleBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CoupleBoardRepository extends JpaRepository<CoupleBoard, Long> {

    /**
     * 지정된 날짜에 작성된 게시물 찾기
     * @param createDate
     * @return
     */
    List<CoupleBoard> findAllByCreatedDate(LocalDate createDate);
}
