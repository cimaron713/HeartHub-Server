package com.umc_spring.Heart_Hub.board.service.mission;

import com.umc_spring.Heart_Hub.board.dto.mission.MissionDto;
import com.umc_spring.Heart_Hub.board.model.mission.Mission;
import com.umc_spring.Heart_Hub.board.model.mission.UserMissionStatus;
import com.umc_spring.Heart_Hub.board.repository.mission.MissionRepository;
import com.umc_spring.Heart_Hub.board.repository.mission.UserMissionStatusRepository;
import com.umc_spring.Heart_Hub.user.model.User;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MissionServiceImpl implements MissionService{

    private final MissionRepository missionRepository;
    private final UserRepository userRepository;
    private final UserMissionStatusRepository umsRepository;

    public void addMissionToUser(MissionDto.MissionRequestDto missionRequestDto) {
        Mission mission = missionRepository.save(missionRequestDto.toEntity());
        List<User> userList = userRepository.findAll();

        if(!userList.isEmpty()) {
            for(User user : userList) {
                UserMissionStatus ums = new UserMissionStatus(mission, user);
                umsRepository.save(ums);
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
