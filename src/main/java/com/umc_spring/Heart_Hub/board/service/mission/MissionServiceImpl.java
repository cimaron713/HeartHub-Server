package com.umc_spring.Heart_Hub.board.service.mission;

import com.umc_spring.Heart_Hub.board.dto.mission.MissionDto;
import com.umc_spring.Heart_Hub.board.model.mission.Mission;
import com.umc_spring.Heart_Hub.board.model.mission.UserMissionStatus;
import com.umc_spring.Heart_Hub.board.repository.mission.MissionRepository;
import com.umc_spring.Heart_Hub.board.repository.mission.ums.UserMissionStatusRepository;
import com.umc_spring.Heart_Hub.user.model.User;
import com.umc_spring.Heart_Hub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
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
        Random random = new Random();
        List<User> userList = userRepository.findAll();
        List<MissionDto.RandomMissionRespDto> randomMissions = new ArrayList<>();

        for(User user : userList) {
            List<Long> missionIdList = umsRepository.getMissionIds(user.getUserId());

            List<Long> randomMissionIdList = new ArrayList<>();
            for(int i=0; i<4; i++) {
                randomMissionIdList.add(missionIdList.get(random.nextInt(missionIdList.size())));
            }

            for(Long missionId : randomMissionIdList) {
                String contentById = missionRepository.getContentById(missionId);

                MissionDto.RandomMissionRespDto randomMissionRespDto = MissionDto.RandomMissionRespDto.builder()
                        .missionContent(contentById)
                        .user(user)
                        .build();

                randomMissions.add(randomMissionRespDto);
            }

        }

        return randomMissions;
    }
}

