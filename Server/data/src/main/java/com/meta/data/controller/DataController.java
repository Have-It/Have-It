package com.meta.data.controller;

import com.meta.data.dto.request.DataCreateRequestDto;
import com.meta.data.dto.response.*;
import com.meta.data.entity.Data;
import com.meta.data.global.CustomApi;
import com.meta.data.global.JwtTokenProvider;
import com.meta.data.global.error.BusinessException;
import com.meta.data.global.error.ErrorCode;
import com.meta.data.global.error.ErrorResponse;
import com.meta.data.repository.DataRepository;
import com.meta.data.service.DataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/data")
public class DataController {
    private final DataService dataService;
    private final JwtTokenProvider jwtTokenProvider;
//    private final MemberServiceClient memberServiceClient;

    @Operation(summary = "서버용", description = "서버용 url")
    @CustomApi
    @PostMapping
    public ResponseEntity<String> createData(@RequestBody @Valid DataCreateRequestDto request){
        dataService.createData(request);
        return ResponseEntity.ok("data DB Success");
    }

    @Operation(summary = "홈 페이지", description = "모바일의 홈페이지에 필요한 정보를 제공하는 메서드입니다."+"\n\n### [ 참고사항 ]\n\n"+"- 닉네임, 프로필 이미지는 로그인한 뒤 받은 값을 저장해서 사용해 주시거나 /member/home url주소로 받아주세요.\n\n"+"- 닉네임 변경 시 응답 값인 새 nickName값을 저장해서 사용해 주세요\n\n"+"- 반환값은 코인값, 연속달성일, 오늘 미션별 성공여부 값입니다.\n\n")
    @CustomApi
    @GetMapping("/mobile/home")
    public ResponseEntity<?> home(@RequestHeader("Authorization") @Parameter(hidden = true) final String token){
        Data userData = dataService.findMember("id", jwtTokenProvider.getMember(token));
//        Long memberId = userData.getId();
//        MemberResponseDto memberInfo = memberServiceClient.getMember(memberId);
//        String nickname = memberInfo.getNickname();
//        String profileImage = memberInfo.getProfileImage();
        HomeResponseDto homeResponseDto = new HomeResponseDto(userData);

        return ResponseEntity.ok(homeResponseDto);
    }

    @Operation(summary = "세팅 페이지", description = "모바일의 세팅페이지에 필요한 정보를 제공하는 메서드입니다."+"\n\n### [ 참고사항 ]\n\n"+"- 닉네임, 프로필 이미지는 로그인한 뒤 받은 값을 저장해서 사용해 주세요 /member/setting\n\n"+"- 닉네임 변경 시 응답 값인 새 nickName값을 저장해서 사용해 주세요\n\n"+"- 반환값은 캐릭터 체중, 캐릭터 다크서클 값입니다.\n\n")
    @CustomApi
    @GetMapping("/mobile/setting")
    public ResponseEntity<?> setting(@RequestHeader("Authorization") @Parameter(hidden = true) final String token){
        Data userData = dataService.findMember("id", jwtTokenProvider.getMember(token));
        SettingResponseDto settingResponseDto = new SettingResponseDto(userData);

        return ResponseEntity.ok(settingResponseDto);
    }

    @Operation(summary = "유니티 캐릭터 스탯 정보", description = "유니티 로그인 후 광장에서 필요한 캐릭터 스탯 정보를 제공하는 메서드입니다."+"\n\n### [ 참고사항 ]\n\n"+"- 닉네임은 로그인한 뒤 받은 값을 저장해서 사용해 주세요\n\n"+"- 반환값은 캐릭터 체중(=>스피드), 캐릭터 다크서클(=>힘), 펫 유무 값입니다.\n\n")
    @CustomApi
    @GetMapping("/unity/status")
    public ResponseEntity<?> unityStatus(@RequestHeader("Authorization") @Parameter(hidden = true) final String token){
        Data userData = dataService.findMember("id", jwtTokenProvider.getMember(token));
        StatusResponseDto statusResponseDto = new StatusResponseDto(userData);
        return ResponseEntity.ok(statusResponseDto);
    }

    @Operation(summary = "서버용", description = "서버용 url")
    @CustomApi
    @PostMapping("/{memberId}/{price}")
    public ResponseEntity<?> buyCoin(@PathVariable Long memberId, @PathVariable int price) {
        log.info("컨트롤러 들어옴");
        try {
            BuyResponseDto response = dataService.buyCoin(memberId, price);
            log.info(String.valueOf(response));
            return ResponseEntity.ok(response);
        } catch (BusinessException e) {
            ErrorCode errorCode = e.getErrorCode();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("코인 부족");
        }
    }

    @Operation(summary = "서버용", description = "서버용 url")
    @CustomApi
    @PutMapping("/update/nickname")
    public void update(@RequestParam Long id, @RequestParam String nickName) {
        dataService.update(id, nickName);
    }


    @Operation(summary = "서버용", description = "서버용 url")
    @GetMapping("/testDay")
    public String testDay(@RequestHeader("Authorization") @Parameter(hidden = true) final String token, @RequestParam int number) {
        Data userData = dataService.findMember("id", jwtTokenProvider.getMember(token));
        return dataService.testDay(userData, number);
    }

}
