package com.meta.store.controller;

import com.meta.store.dto.response.StoreResponseDto;
import com.meta.store.global.CustomApi;
import com.meta.store.global.JwtTokenProvider;
import com.meta.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

    private final JwtTokenProvider jwtTokenProvider;
    private final StoreService storeService;

    @Operation(summary = "헤어 상점", description = "헤어 상점에서 있는 모든 아이템과 가격 소유여부를 반환하는 메서드입니다.")
    @CustomApi
    @GetMapping("/hair")
    public ResponseEntity<?> getHairStore (@RequestHeader("Authorization") @Parameter(hidden = true) final String token){
        Long memberId = Long.valueOf(jwtTokenProvider.getMember(token));
        log.info(String.valueOf(memberId));
        // 상점 아이템리스트 + 보유여부
        List<StoreResponseDto> response = storeService.getAllWithOwnership(memberId,"hair");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "악세사리 상점", description = "악세사리 상점에서 있는 모든 아이템과 가격 소유여부를 반환하는 메서드입니다.")
    @CustomApi
    @GetMapping("/acc")
    public ResponseEntity<?> getAccStore (@RequestHeader("Authorization") @Parameter(hidden = true) final String token){
        Long memberId = Long.valueOf(jwtTokenProvider.getMember(token));
        List<StoreResponseDto> response = storeService.getAllWithOwnership(memberId,"acc");
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "상의 상점", description = "상의 상점에서 있는 모든 아이템과 가격 소유여부를 반환하는 메서드입니다.")
    @CustomApi
    @GetMapping("/top")
    public ResponseEntity<?> getTopStore (@RequestHeader("Authorization") @Parameter(hidden = true) final String token){
        Long memberId = Long.valueOf(jwtTokenProvider.getMember(token));
        List<StoreResponseDto> response = storeService.getAllWithOwnership(memberId,"top");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "하의 상점", description = "하의 상점에서 있는 모든 아이템과 가격 소유여부를 반환하는 메서드입니다.")
    @CustomApi
    @GetMapping("/bottom")
    public ResponseEntity<?> getBottomStore (@RequestHeader("Authorization") @Parameter(hidden = true) final String token){
        Long memberId = Long.valueOf(jwtTokenProvider.getMember(token));
        List<StoreResponseDto> response = storeService.getAllWithOwnership(memberId,"bottom");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "신발 상점", description = "신발 상점에서 있는 모든 아이템과 가격 소유여부를 반환하는 메서드입니다.")
    @CustomApi
    @GetMapping("/shoes")
    public ResponseEntity<?> getShoesStore (@RequestHeader("Authorization") @Parameter(hidden = true) final String token){
        Long memberId = Long.valueOf(jwtTokenProvider.getMember(token));
        List<StoreResponseDto> response = storeService.getAllWithOwnership(memberId,"shoes");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "펫 상점", description = "펫 상점에서 있는 모든 아이템과 가격 소유여부를 반환하는 메서드입니다.")
    @CustomApi
    @GetMapping("/pet")
    public ResponseEntity<?> getPetStore (@RequestHeader("Authorization") @Parameter(hidden = true) final String token){
        Long memberId = Long.valueOf(jwtTokenProvider.getMember(token));
        List<StoreResponseDto> response = storeService.getAllWithOwnership(memberId,"pet");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "얼굴 상점", description = "얼굴 상점에서 있는 모든 아이템과 가격 소유여부를 반환하는 메서드입니다.")
    @CustomApi
    @GetMapping("/face")
    public ResponseEntity<?> getFaceStore (@RequestHeader("Authorization") @Parameter(hidden = true) final String token){
        Long memberId = Long.valueOf(jwtTokenProvider.getMember(token));
        List<StoreResponseDto> response = storeService.getAllWithOwnership(memberId,"face");
        return ResponseEntity.ok(response);
    }

}
