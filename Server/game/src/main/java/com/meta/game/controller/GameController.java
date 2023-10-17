package com.meta.game.controller;

import com.meta.game.dto.request.GameResultRequestDto;
import com.meta.game.dto.response.GameRankDto;
import com.meta.game.dto.response.GameResultResponseDto;
import com.meta.game.global.CustomApi;
import com.meta.game.global.JwtTokenProvider;
import com.meta.game.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/result")
    @Operation(summary = "게임 결과 데이터 업데이트", description = "게임에 참여한 유저의 userId값과 승리유무를 입력해주세요.")
    public ResponseEntity<?> updateResultData(@RequestBody GameResultRequestDto request){
        GameResultResponseDto response = gameService.updateResult(request.getMemberId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/rank")
    @Operation(summary = "게임 순위 호출")
    public ResponseEntity<?> rankGame(){
        List<GameRankDto> gameRanks = gameService.getTop();
        return ResponseEntity.ok(gameRanks);
    }

    @Operation(summary = "서버용", description = "서버용 url")
    @CustomApi
    @PutMapping("/update/nickname")
    public void update(@RequestParam Long id, @RequestParam String nickName) {
        gameService.update(id, nickName);
    }

}
