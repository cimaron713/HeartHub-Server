package com.umc_spring.Heart_Hub.board.repository.mission;

import com.umc_spring.Heart_Hub.board.model.mission.UserMissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMissionStatusRepository extends JpaRepository<UserMissionStatus, Long> {

}
