package com.meta.data.controller;

import com.meta.data.dto.response.TopSuccessResultDto;
import com.meta.data.global.CustomApi;
import com.meta.data.service.DataService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/rankdata1")
public class RankController {

    private final DataService dataService;

    @Operation(summary = "서버용", description = "서버용 url")
    @GetMapping
    public ResponseEntity<?> rankSuccess() {
        TopSuccessResultDto highestScorersIds = dataService.getTopSuccess();
        return ResponseEntity.ok(highestScorersIds);
    }


}
