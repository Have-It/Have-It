package com.meta.character.client;

import com.meta.character.dto.request.TopSuccessRequestDto;
import com.meta.character.dto.response.BuyResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "DATA-SERVICE", url = "http://j9d201.p.ssafy.io:8012")
public interface DataServiceClient {
    @PostMapping("/data/{memberId}/{price}")
    ResponseEntity<BuyResponseDto> buyCoin(@PathVariable("memberId") Long memberId, @PathVariable("price") int price);


    @GetMapping("/rankdata1")
    ResponseEntity<TopSuccessRequestDto> rankSuccess();
}
