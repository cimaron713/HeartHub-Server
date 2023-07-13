package com.umc_spring.Heart_Hub.board.controller.mission;

import com.umc_spring.Heart_Hub.board.dto.mission.MissionDto;
import com.umc_spring.Heart_Hub.board.service.mission.MissionService;
import com.umc_spring.Heart_Hub.board.service.mission.MissionServiceImpl;
import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @PostMapping("/api/mission/add")
    public ResponseEntity<ApiResponse<String>> addMissionToUser(MissionDto.MissionRequestDto missionRequestDto) {

        missionService.addMissionToUser(missionRequestDto);

        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent("Success add Mission To User"));
    }
}
