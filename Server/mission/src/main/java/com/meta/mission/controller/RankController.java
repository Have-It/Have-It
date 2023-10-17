package com.meta.mission.controller;

import com.meta.mission.dto.response.TopKcalResultDto;
import com.meta.mission.dto.response.TopWalkResultDto;
import com.meta.mission.entity.Mission;
import com.meta.mission.global.CustomApi;
import com.meta.mission.global.error.BusinessException;
import com.meta.mission.global.error.ErrorCode;
import com.meta.mission.repository.MissionRepository;
import com.meta.mission.service.MissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Tag(name = "Rank", description = "유니티 API ")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/rankdata")
public class RankController {

    private final MissionService missionService;

    @Operation(summary = "서버용!", description = "서버용 url")
    @GetMapping("/2")
    public ResponseEntity<?> rankKcal() {
        TopKcalResultDto highestScorersInfo = missionService.getTopKcal();
        return ResponseEntity.ok(highestScorersInfo);
    }

    @Operation(summary = "서버용", description = "서버용 url")
    @GetMapping("/3")
    public ResponseEntity<?> rankWalk() {
        TopWalkResultDto highestScorersIds = missionService.getTopWalk();
        return ResponseEntity.ok(highestScorersIds);
    }

}
