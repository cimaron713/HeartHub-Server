package com.umc_spring.Heart_Hub.board.dto.mission;

import com.umc_spring.Heart_Hub.board.model.mission.Mission;
import com.umc_spring.Heart_Hub.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MissionDto {

    @Getter
    @NoArgsConstructor
    public static class MissionRequestDto{
        private String content;

        public Mission toEntity(User user) {
            return Mission.builder()
                    .content(content)
                    .user(user)
                    .build();
        }
    }
}
