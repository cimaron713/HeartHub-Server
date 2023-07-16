package com.umc_spring.Heart_Hub.board.model.community;

import com.umc_spring.Heart_Hub.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockedList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blockedListId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User blocker;

    @ManyToOne(fetch = FetchType.LAZY)
    private User blockedUser;
}
