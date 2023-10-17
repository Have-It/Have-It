package com.meta.data.controller;


import com.meta.data.dto.response.MissionSucessResponseDto;
import com.meta.data.entity.Data;
import com.meta.data.global.CustomApi;
import com.meta.data.global.JwtTokenProvider;
import com.meta.data.service.DataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/success")
public class SuccessDataController {

    private final DataService dataService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "첫 번째 미션 (운동) 성공 버튼", description = "운동 미션을 성공하면 보상을 받는 메서드입니다."+"\n\n### [ 참고사항 ]\n\n"+"- 하루에 한 번만 누를 수 있습니다.\n\n")
    @CustomApi
    @PostMapping("/exercise")
    public ResponseEntity<?> mission1test (@RequestHeader("Authorization") @Parameter(hidden = true) final String token) {
        Data data = dataService.findMember("id", jwtTokenProvider.getMember(token));
        dataService.setMission1Success(data);
        return ResponseEntity.ok().body(new MissionSucessResponseDto(data));
    }

    @Operation(summary = "두 번째 미션 (수면) 성공 버튼", description = "수면 미션을 성공하면 보상을 받는 메서드입니다."+"\n\n### [ 참고사항 ]\n\n"+"- 하루에 한 번만 누를 수 있습니다.\n\n")
    @CustomApi
    @PostMapping("/sleep")
    public ResponseEntity<?> mission2 (@RequestHeader("Authorization") @Parameter(hidden = true) final String token) {
        Data data = dataService.findMember("id", jwtTokenProvider.getMember(token));
        dataService.setMission2Success(data);
        return ResponseEntity.ok().body(new MissionSucessResponseDto(data));
    }

    @Operation(summary = "세 번째 미션 (사용자 맞춤) 성공 버튼", description = "자유 미션을 성공하면 보상을 받는 메서드입니다."+"\n\n### [ 참고사항 ]\n\n"+"- 하루에 한 번만 누를 수 있습니다.\n\n")
    @CustomApi
    @PostMapping("/customMission")
    public ResponseEntity<?> mission3 (@RequestHeader("Authorization") @Parameter(hidden = true) final String token) {
        Data data = dataService.findMember("id", jwtTokenProvider.getMember(token));
        dataService.setMission3Success(data);
        return ResponseEntity.ok().body(new MissionSucessResponseDto(data));
    }

}
