package com.meta.member.component;


import com.meta.member.dto.request.MissionCreateRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "MISSION-SERVICE", url = "http://j9d201.p.ssafy.io:8013/mission")
//@FeignClient(name = "MISSION-SERVICE", url = "http://localhost:8013/mission")
public interface MissionClientComponent {
    @PostMapping
    ResponseEntity<String> creatMission(MissionCreateRequestDto request);

    @PutMapping("/update/nickname")
    void update(@RequestParam Long id, @RequestParam String nickName);
}
