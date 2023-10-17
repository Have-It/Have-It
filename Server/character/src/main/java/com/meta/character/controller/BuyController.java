package com.meta.character.controller;

import com.meta.character.dto.request.BuyRequestDto;
import com.meta.character.dto.response.BuyResponseDto;
import com.meta.character.global.CustomApi;
import com.meta.character.global.JwtTokenProvider;
import com.meta.character.service.CharacterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/buy")
public class BuyController {
    private final JwtTokenProvider jwtTokenProvider;
    private final CharacterService characterService;

    @Operation(summary = "아이템 구매", description = "아이템을 구매하고 남은 코인을 반환하는 메서드입니다.")
    @CustomApi
    @PostMapping
    public ResponseEntity<?> buyItem (@RequestHeader("Authorization") @Parameter(hidden = true) final String token,
                                      @RequestBody BuyRequestDto request) {
        Long memberId = Long.valueOf(jwtTokenProvider.getMember(token));
        log.info(String.valueOf(memberId));
        BuyResponseDto response = characterService.buyItem(memberId, request);
        return ResponseEntity.ok(response);
    }

}
