package com.meta.member.component;

import com.meta.member.dto.request.DataCreateRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(name = "DATA-SERVICE", url = "http://192.168.100.117:8012/data")
//@FeignClient(name = "DATA-SERVICE", url = "http://localhost:8012/data")
@FeignClient(name = "DATA-SERVICE", url = "http://j9d201.p.ssafy.io:8012/data")
public interface DataClientComponent {
    @PostMapping
    ResponseEntity<String> createData(DataCreateRequestDto request);

    @PutMapping("/update/nickname")
    void update(@RequestParam Long id, @RequestParam String nickName);
}