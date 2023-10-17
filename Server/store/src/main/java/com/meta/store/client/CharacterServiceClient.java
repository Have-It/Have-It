package com.meta.store.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "CHARACTER-SERVICE", url = "http://j9d201.p.ssafy.io:8015/character")
public interface CharacterServiceClient {
    @GetMapping("/items/{memberId}")
    List<Long> getOwnedItemIdsByType(@PathVariable Long memberId, @RequestParam String type);
}
