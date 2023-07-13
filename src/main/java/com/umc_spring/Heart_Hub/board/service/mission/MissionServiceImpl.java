package com.umc_spring.Heart_Hub.board.service.mission;

import com.umc_spring.Heart_Hub.board.dto.mission.MissionDto;
import com.umc_spring.Heart_Hub.board.model.mission.Mission;
import com.umc_spring.Heart_Hub.board.repository.mission.MissionRepository;
import com.umc_spring.Heart_Hub.user.model.User;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@NoArgsConstructor
public class MissionServiceImpl implements MissionService{

    private MissionRepository missionRepository;
    private UserRepository userRepository;

    public void addMissionToUser(MissionDto.MissionRequestDto missionRequestDto) {
        List<User> userList = userRepository.findAll();

        if(!userList.isEmpty()) {
            for(User user : userList) {
                Mission mission = missionRequestDto.toEntity(user);
                missionRepository.save(mission);
            }
        }
    }

    public List<MissionDto.RandomMissionRespDto> getMissions() {
        List<User> userList = userRepository.findAll();
        List<MissionDto.RandomMissionRespDto> randomMissions = new ArrayList<>();

        for(User user : userList) {
            List<Mission> missions = missionRepository.getMissions(user.getUserId());
            MissionDto.RandomMissionRespDto dtoMissions = MissionDto.RandomMissionRespDto.builder()
                    .missions(missions)
                    .user(user)
                    .build();

            randomMissions.add(dtoMissions);
        }

        return randomMissions;
    }
}
