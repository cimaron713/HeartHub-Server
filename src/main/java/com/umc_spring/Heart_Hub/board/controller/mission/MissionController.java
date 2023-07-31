package com.umc_spring.Heart_Hub.board.controller.mission;

import com.umc_spring.Heart_Hub.board.dto.mission.MissionDto;
import com.umc_spring.Heart_Hub.board.service.mission.MissionService;
import com.umc_spring.Heart_Hub.board.service.mission.MissionServiceImpl;
import com.umc_spring.Heart_Hub.constant.dto.ApiResponse;
import com.umc_spring.Heart_Hub.constant.enums.CustomResponseStatus;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class MissionController {

    private final MissionService missionService;

    @PostMapping("/admin/mission/add")
    public ResponseEntity<ApiResponse<String>> addMissionToUser(MissionDto.MissionRequestDto missionRequestDto) {
        log.info("missionRequestDto : " + missionRequestDto.getContent());
        missionService.addMissionToUser(missionRequestDto);

        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/user/missions")
    public ResponseEntity<ApiResponse<List<MissionDto.RandomMissionRespDto>>> getMissions() {
        List<MissionDto.RandomMissionRespDto> randomMissionRespDtos = missionService.getMissions();

        return ResponseEntity.ok().body(ApiResponse.createSuccess(randomMissionRespDtos, CustomResponseStatus.SUCCESS));
    }

    /**
     * checkStatus 상태 변경 api
     */
    @PutMapping("/user/mission/{missionId}")
    public ResponseEntity<ApiResponse<Long>> checkStatusModify(@PathVariable Long missionId,
                                                               Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Long umsId = missionService.checkStatusModify(missionId, username);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(umsId, CustomResponseStatus.SUCCESS));

    }

    /**
     * deleteStatus 상태 변경 api (mission 삭제)
     */
    @DeleteMapping("/admin/mission/{missionId}")
    public ResponseEntity<ApiResponse<Long>> deleteMission(@PathVariable Long missionId) {
        missionService.deleteMission(missionId);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(missionId, "Success Delete Mission"));

    }


}
