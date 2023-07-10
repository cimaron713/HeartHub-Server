package com.umc_spring.Heart_Hub.coupleBoard.repository;

import com.umc_spring.Heart_Hub.coupleBoard.model.CoupleBoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<CoupleBoardImage, Long> {
}
