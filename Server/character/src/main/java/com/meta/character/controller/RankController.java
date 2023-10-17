package com.meta.character.controller;

import com.meta.character.dto.response.TopKcalResultDto;
import com.meta.character.dto.response.TopSuccessResultDto;
import com.meta.character.dto.response.TopWalkResultDto;
import com.meta.character.global.CustomApi;
import com.meta.character.service.CharacterService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/rank")
public class RankController {

    private final CharacterService characterService;

    @Operation(summary = "연속성공 랭킹 왕", description = "연속 성공 일 수 가 제일 많은 랭킹 1위 리스트를 보내는 메서드입니다."+"\n\n### [ 참고사항 ]\n\n"+"- 동점자들은 리스트 형태로 제공됩니다.\n\n")
    @CustomApi
    @GetMapping("/success")
    public ResponseEntity<?> rankSuccess() {
        TopSuccessResultDto highestScorersIds = characterService.getTopSuccess();
        return ResponseEntity.ok(highestScorersIds);
    }

    @Operation(summary = "칼로리 랭킹 왕", description = "전날 칼로리를 제일 많이 소모한 랭킹 1위 리스트를 보내는 메서드입니다."+"\n\n### [ 참고사항 ]\n\n"+"- 동점자들은 리스트 형태로 제공됩니다.\n\n")
    @CustomApi
    @GetMapping("/kcal")
    public ResponseEntity<?> rankKcal() {
        TopKcalResultDto highestScorersInfo = characterService.getTopKcal();
        return ResponseEntity.ok(highestScorersInfo);
    }

    @Operation(summary = "걷기 랭킹 왕", description = "전날 걸음 수가 제일 많은 랭킹 1위 리스트를 보내는 메서드입니다."+"\n\n### [ 참고사항 ]\n\n"+"- 동점자들은 리스트 형태로 제공됩니다.\n\n")
    @CustomApi
    @GetMapping("/walk")
    public ResponseEntity<?> rankWalk() {
        log.info("cotroller들어옴");
        TopWalkResultDto highestScorersIds = characterService.getTopWalk();
        return ResponseEntity.ok(highestScorersIds);
    }

}
