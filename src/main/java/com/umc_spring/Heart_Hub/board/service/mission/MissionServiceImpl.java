package com.umc_spring.Heart_Hub.board.service.mission;

import com.umc_spring.Heart_Hub.board.dto.mission.MissionDto;
import com.umc_spring.Heart_Hub.board.model.mission.Mission;
import com.umc_spring.Heart_Hub.board.model.mission.UserMissionStatus;
import com.umc_spring.Heart_Hub.board.repository.mission.MissionRepository;
import com.umc_spring.Heart_Hub.board.repository.mission.ums.UserMissionStatusRepository;
import com.umc_spring.Heart_Hub.constant.enums.ErrorCode;
import com.umc_spring.Heart_Hub.constant.exception.CustomException;
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

    @Override
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
    @Override
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


    /**
     * checkStatus 상태 변경 api
     */
    @Override
    public Long checkStatusModify(Long missionId, String username) {
        Long userId = userRepository.getUserIdByUserName(username);
        Long umsId = umsRepository.findUMSByMissionIdAndUserId(missionId, userId);
        UserMissionStatus userMissionStatus = umsRepository.findById(umsId).orElseThrow(() -> {
            throw new CustomException(ErrorCode.USER_MISSION_STATUS_NOT_FOUND);
        });

        userMissionStatus.checkStatusModify();
        return umsId;
    }

    /**
     * deleteStatus 상태 변경 api (mission 삭제)
     */
    @Override
    public void deleteMission(Long missionId) {
        Mission mission = missionRepository.findById(missionId).orElseThrow(()-> {
            throw new CustomException(ErrorCode.MISSION_NOT_FOUND);
        });
        mission.deleteStatusModify();
    }
}

