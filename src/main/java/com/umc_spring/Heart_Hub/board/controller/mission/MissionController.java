package com.umc_spring.Heart_Hub.board.controller.mission;

import com.umc_spring.Heart_Hub.board.dto.mission.MissionDto;
import com.umc_spring.Heart_Hub.board.service.mission.MissionService;
import com.umc_spring.Heart_Hub.board.service.mission.MissionServiceImpl;
import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MissionController {

    private final MissionService missionService;

    @PostMapping("/api/mission/add")
    public ResponseEntity<ApiResponse<String>> addMissionToUser(MissionDto.MissionRequestDto missionRequestDto) {
        log.info("missionRequestDto : " + missionRequestDto.getContent());
        missionService.addMissionToUser(missionRequestDto);

        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent("Success add Mission To User"));
    }

    @GetMapping("/api/missions")
    public ResponseEntity<ApiResponse<List<MissionDto.RandomMissionRespDto>>> getMissions() {
        List<MissionDto.RandomMissionRespDto> randomMissionRespDtos = missionService.getMissions();

        return ResponseEntity.ok().body(ApiResponse.createSuccess(randomMissionRespDtos, "Success Get Missions"));
    }

    /**
     * checkStatus 상태 변경 api
     */

    /**
     * deleteStatus 상태 변경 api (mission 삭제)
     */
}
