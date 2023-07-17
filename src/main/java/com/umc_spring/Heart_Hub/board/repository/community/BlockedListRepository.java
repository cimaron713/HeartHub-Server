package com.umc_spring.Heart_Hub.board.repository.community;

import com.umc_spring.Heart_Hub.board.model.community.BlockedList;
import com.umc_spring.Heart_Hub.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlockedListRepository extends JpaRepository<BlockedList, Long> {

    BlockedList findByBlockerAndBlockedUser(User blocker, User blockedUser);
    List<BlockedList> findAllByBlocker(User blocker);
}
