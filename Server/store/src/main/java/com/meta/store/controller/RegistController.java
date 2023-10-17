package com.meta.store.controller;

import com.meta.store.dto.request.RegistRequestDto;
import com.meta.store.global.CustomApi;
import com.meta.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/regist")
public class RegistController {

    private final StoreService storeService;

    @Operation(summary = "상점 아이템 등록", description = "상점에 새 아이템을 등록하는 메서드입니다.")
    @CustomApi
    @PostMapping
    public ResponseEntity<String> registStoreItem (@RequestBody RegistRequestDto request){
        storeService.registerItem(request);
        return ResponseEntity.ok("등록 완료");
    }
    
}
