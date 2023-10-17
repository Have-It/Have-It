package com.meta.character.controller;

import com.meta.character.dto.request.UpdateCharacterRequestDto;
import com.meta.character.dto.response.CharacterInfoResponseDto;
import com.meta.character.dto.response.TopSuccessResultDto;
import com.meta.character.global.CustomApi;
import com.meta.character.global.JwtTokenProvider;
import com.meta.character.service.CharacterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/character")
public class CharacterController {

    private final JwtTokenProvider jwtTokenProvider;
    private final CharacterService characterService;

    @Operation(summary = "모든 아이템", description = "캐릭터가 지니고 있는 모든 아이템 값을 반환하는 메서드입니다.")
    @CustomApi
    @GetMapping("/info")
    public ResponseEntity<CharacterInfoResponseDto> createCharacter(@RequestHeader("Authorization") @Parameter(hidden = true) final String token){
        Long memberId = Long.valueOf(jwtTokenProvider.getMember(token));
        CharacterInfoResponseDto response = characterService.getCharacter(memberId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "아이템 갈아입기", description = "인벤토리에서 캐릭터의 아이템을 변환하는 메서드입니다.")
    @CustomApi
    @PutMapping("/change")
    public ResponseEntity<CharacterInfoResponseDto> updateCharacter(@RequestHeader("Authorization") @Parameter(hidden = true) final String token,
                                                                   @RequestBody UpdateCharacterRequestDto request){
        Long memberId = Long.valueOf(jwtTokenProvider.getMember(token));
        CharacterInfoResponseDto response = characterService.updateCharacter(memberId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "서버용", description = "서버용 url")
    @GetMapping("/items/{memberId}")
    List<Long> getOwnedItemIdsByType(@PathVariable Long memberId, @RequestParam String type){
        log.info("요청 컨트롤러 들어옴"+memberId+type);
        List<Long> response = characterService.getItemTypeId(memberId, type);
        log.info(String.valueOf(response));
        return response;
    }

}
