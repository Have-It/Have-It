package com.meta.character.client;

import com.meta.character.dto.request.TopKcalRequestDto;
import com.meta.character.dto.request.TopWalkRequestDto;
import com.meta.character.dto.response.TopWalkResultDto;
import com.meta.character.global.CustomApi;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "MISSION-SERVICE", url = "http://j9d201.p.ssafy.io:8013/rankdata")
public interface MissionServiceClient {
    @GetMapping("/2")
    ResponseEntity<TopKcalRequestDto> rankKcal();

    @GetMapping("/3")
    ResponseEntity<TopWalkRequestDto> rankWalk();
}
