package com.meta.member.component;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "GAME-SERVICE", url = "http://j9d201.p.ssafy.io:8016/game")
public interface GameClientComponent {
    @PutMapping("/update/nickname")
    void update(@RequestParam Long id, @RequestParam String nickName);
}
