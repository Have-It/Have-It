package com.meta.mission.controller;

import com.meta.mission.dto.request.MissionCreateRequestDto;
import com.meta.mission.dto.request.MissionRecordRequestDto;
import com.meta.mission.dto.request.RankRecordRequestDto;
import com.meta.mission.dto.response.MissionRecordResponseDto;
import com.meta.mission.dto.response.RankRecordResponseDto;
import com.meta.mission.dto.response.TopKcalResultDto;
import com.meta.mission.dto.response.TopWalkResultDto;
import com.meta.mission.entity.Mission;
import com.meta.mission.global.CustomApi;
import com.meta.mission.global.JwtTokenProvider;
import com.meta.mission.service.MissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Mission", description = "모바일 API ")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mission")
public class MissionController {

    private final MissionService missionService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "서버용", description = "서버용 url")
    @PostMapping
    public ResponseEntity<String> createMission(@RequestBody @Valid MissionCreateRequestDto request){
        missionService.createMission(request);
        return ResponseEntity.ok("mission DB Success");
    }

//    @GetMapping("/record")
//    ResponseEntity<MissionRecordResponseDto> dailyMission(@RequestHeader("Authorization") @Parameter(hidden = true) final String token, @RequestBody @Valid MissionRecordRequestDto request) {
//        Mission mission = missionService.findMember("id", jwtTokenProvider.getMember(token));
//        missionService.record(mission, request);
//        MissionRecordResponseDto response = new MissionRecordResponseDto();
//        return ResponseEntity.ok(response);
//    }

    @Operation(summary = "미션 결과 값 저장", description = "모바일에서 새벽 3시에 맴버 별 kcal, 걸음 수를 보내는 메서드입니다."+"\n\n### [ 참고사항 ]\n\n"+"- 새벽 3시에 url을 호출해주세요\n\n")
    @CustomApi
    @PostMapping("/save")
    public ResponseEntity<RankRecordResponseDto> dailyMission(@RequestHeader("Authorization") @Parameter(hidden = true) final String token, @RequestBody @Valid RankRecordRequestDto request) {
        Long memberId = Long.valueOf(jwtTokenProvider.getMember(token));
        Mission mission = missionService.rankRecord(memberId, request);
        RankRecordResponseDto response = new RankRecordResponseDto(mission);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "서버용", description = "서버용 url")
    @CustomApi
    @PutMapping("/update/nickname")
    public void update(@RequestParam Long id, @RequestParam String nickName) {
        missionService.update(id, nickName);
    }

}
