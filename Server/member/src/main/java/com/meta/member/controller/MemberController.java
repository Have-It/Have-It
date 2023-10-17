package com.meta.member.controller;

import com.meta.member.dto.client.KakaoTokenClient;
import com.meta.member.dto.request.*;
import com.meta.member.dto.response.*;
import com.meta.member.entity.Member;
import com.meta.member.exception.InvalidedAccessTokenException;
import com.meta.member.global.CustomApi;
import com.meta.member.repository.MemberRepository;
import com.meta.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final KakaoTokenClient kakaoTokenClient;
    private final MemberService memberService;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    @Operation(summary = "일반 회원가입", description = "회원가입 메서드입니다."+"\n\n### [ 참고사항 ]\n\n"+"- 회원가입을 위해서는 email, nickname, password 값이 주어져야 합니다. \n\n"+"- 회원가입 로직에서도 이메일 중복검사, 닉네임 중복검사가 이루어집니다.\n\n"+"- 일반 회원가입의 경우 socialtype은 null값입니다.\n\n")
    @CustomApi
    @PostMapping("/signIn")
    public ResponseEntity<Map<String, String>> signUp(@RequestBody @Validated MemberSignupRequestDto memberSignupRequestDto) {
        memberService.signUp(memberSignupRequestDto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "회원가입 성공");
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "이메일 중복검사", description = "이메일이 이미 있는지 확인하는 메서드입니다."+"\n\n### [ 참고사항 ]\n\n"+"- 이미 있는 이메일이라면 result값이 fail입니다. \n\n"+"- 회원가입 로직에서도 이메일 중복검사가 존재합니다.\n\n")
    @CustomApi
    @PostMapping("/check/email")
    public ResponseEntity<MessageResponseDto> checkEmail(@RequestParam String email) {
        MessageResponseDto response = memberService.checkEmail(email);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "닉네임 중복검사", description = "닉네임이 이미 있는지 확인하는 메서드입니다."+"\n\n### [ 참고사항 ]\n\n"+"- 이미 있는 닉네임이라면 result값이 fail입니다. \n\n"+"- 회원가입 로직에서도 닉네임 중복검사가 존재합니다.\n\n")
    @CustomApi
    @PostMapping("/check/nickName")
    public ResponseEntity<MessageResponseDto> checkNickName(@RequestParam String nickname) {
        MessageResponseDto response = memberService.checkNickName(nickname);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "로그인", description = "로그인 후 accesstoken을 발급받는 메서드입니다."+"\n\n### [ 참고사항 ]\n\n"+"- 로그인을 위해서는 email, password 값이 주어져야 합니다.  \n\n"+"- 로그인 성공 시 발급되는 accesstoken값을 자물쇠에 넣어주세요.\n\n"+"- 다중기기 로그인이 가능합니다.\n\n")
    @CustomApi
    @PostMapping("/login")
    public ResponseEntity<MemberTokenResponseDto> login(@RequestBody @Validated MemberLoginRequestDto memberLoginRequestDto, HttpServletRequest request) {
        MemberTokenResponseDto response = memberService.login(memberLoginRequestDto, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "소셜 로그인(카카오)", description = "카카오 소셜 로그인 후 accesstoken을 발급받는 메서드입니다."+"\n\n### [ 참고사항 ]\n\n"+"- 소셜 로그인을 위해서는 code 값이 주어져야 합니다.  \n\n"+"- 로그인 성공 시 발급되는 accesstoken값을 자물쇠에 넣어주세요.\n\n"
            +"- 카카오 로그인 시 DB에 없는 사용자의 경우 자동적으로 회원가입이 이루어집니다.\n\n"+"- 카카오 로그인의 경우 이메일은 kakao@sample@email.com 형태로 저장되며 닉네임 중복 시 닉네임_1 형태로 저장되며 중복 닉네임 수 만큼 숫자가 자동으로 카운트 됩니다. \n\n"+"- 일반 회원가입의 경우 socialtype은 kakao입니다.\n\n"+"- 다중기기 로그인이 가능합니다.\n\n")
    @CustomApi
    @GetMapping("/kakao/login")
    public ResponseEntity<MemberTokenResponseDto> kakaoLogin (String code, HttpServletRequest request) throws IOException {
        String contentType = "application/x-www-form-urlencoded;charset=utf-8";
        KakaoTokenDto.Request kakaoTokenRequestDto = KakaoTokenDto.Request.builder()
                .client_id(clientId)
                .client_secret(clientSecret)
                .grant_type("authorization_code")
                .code(code)
//                .redirect_url("http://192.168.100.117:8011/api/member/oauth2/code/kakao")
//                .redirect_url("http://j9d201.p.ssafy.io:8011/api/member/oauth2/code/kakao")
//                .redirect_url("http://localhost:3000/auth")
//                .redirect_url("http://j9d201.p.ssafy.io:3000/meta/auth")
                .redirect_url("https://j9d201.p.ssafy.io/meta/auth")
                .build();

        KakaoTokenDto.Response kakaoToken = kakaoTokenClient.requestKakaoToken(contentType, kakaoTokenRequestDto);
        String token = kakaoToken.getAccess_token();
        MemberTokenResponseDto response = null;
        if(token == null) {
            throw new InvalidedAccessTokenException("다른 방법으로 로그인하세요.");
        } else {
            response = memberService.loginOauth2Kakao(token, request);
        }

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "모바일용 소셜 로그인(카카오) - 1번 2번 중에 어떤게 더 편하신가요??", description = "모바일용 카카오 소셜 로그인 후 accesstoken을 발급받는 두번째 방법과 다르게 카카오에서 받은 accesstoken을 body값으로 요구하고 있는 메서드입니다."+"\n\n### [ 참고사항 ]\n\n"+"- 소셜 로그인을 위해서는 카카오에서 얻은 accesstoken 값을 body에 주어져야 합니다.  \n\n"+"- 로그인 성공 시 발급되는 accesstoken값을 자물쇠에 넣어주세요.\n\n"
            +"- 카카오 로그인 시 DB에 없는 사용자의 경우 자동적으로 회원가입이 이루어집니다.\n\n"+"- 카카오 로그인의 경우 이메일은 kakao@sample@email.com 형태로 저장되며 닉네임 중복 시 닉네임_1 형태로 저장되며 중복 닉네임 수 만큼 숫자가 자동으로 카운트 됩니다. \n\n"+"- 일반 회원가입의 경우 socialtype은 kakao입니다.\n\n"+"- 다중기기 로그인이 가능합니다.\n\n")
    @CustomApi
    @PostMapping("/kakao/login/mobile")
    public ResponseEntity<MemberTokenResponseDto> mobileKakaoLogin1 (HttpServletRequest request, @RequestParam String token) throws IOException {

        MemberTokenResponseDto response = memberService.loginOauth2Kakao(token, request);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "모바일용 소셜 로그인2 (카카오)", description = "모바일용 카카오 소셜 로그인 후 accesstoken을 발급받는 메서드입니다."+"\n\n### [ 참고사항 ]\n\n"+"- 위 방법과 달리 http헤더에서 자동으로 카카오 accesstoken 값을 읽어 옵니다! 다만 지금은 swagger에서 테스트하는 경우이니 임의로 자물쇠에 카카오에서 받은 accesstoken값을 넣어주세요! \n\n"+"- 로그인 성공 시 발급되는 accesstoken값을 자물쇠에 넣어주세요.\n\n"
            +"- 카카오 로그인 시 DB에 없는 사용자의 경우 자동적으로 회원가입이 이루어집니다.\n\n"+"- 카카오 로그인의 경우 이메일은 kakao@sample@email.com 형태로 저장되며 닉네임 중복 시 닉네임_1 형태로 저장되며 중복 닉네임 수 만큼 숫자가 자동으로 카운트 됩니다. \n\n"+"- 일반 회원가입의 경우 socialtype은 kakao입니다.\n\n"+"- 다중기기 로그인이 가능합니다.\n\n")
    @CustomApi
    @PostMapping("/kakao/login/mobile2")
    public ResponseEntity<MemberTokenResponseDto> mobileKakaoLogin2 (HttpServletRequest request) throws IOException{
        // HTTP 헤더에서 access_token 가져오기
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String token = authorizationHeader.substring(7);

        MemberTokenResponseDto response = memberService.loginOauth2Kakao(token, request);

        return ResponseEntity.ok(response);

    }

    @Operation(summary = "로그아웃", description = "로그아웃 메서드입니다."+"\n\n### [ 참고사항 ]\n\n"+"- 로그아웃 시 refreshtoken과 accesstoken은 전부 무효처리 됩니다. 서비스 이용을 위해서는 다시 로그인 해주세요 \n\n")
    @CustomApi
    @PostMapping("/logout")
    public ResponseEntity<Map<String,String>> logout(@RequestHeader("Authorization") @Parameter(hidden = true) final String token) {
        memberService.logout(token);
        Map<String, String> response = new HashMap<>();
        response.put("message", "로그아웃 성공");
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "회원정보 조회", description = "자기 자신의 정보를 조회하는 메서드입니다."+"\n\n### [ 참고사항 ]\n\n"+"- 가입 후 기본값으로 characterWeight 와 characterDarkcircle 값은 12로 주어집니다. \n\n")
    @CustomApi
    @GetMapping("/info")
    public ResponseEntity<MemberMyPageResponseDto> findMyPage(@RequestHeader("Authorization") @Parameter(hidden = true)  final String token) {
        MemberMyPageResponseDto response = memberService.findMyPage(token);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "닉네임 수정", description = "닉네임을 수정하는 메서드입니다."+"\n\n### [ 참고사항 ]\n\n"+"- 이미 있는 닉네임으로 수정은 불가능합니다. \n\n")
    @CustomApi
    @PutMapping("/update/nickname")
    public ResponseEntity<MemberNickNameResponseDto> update(
            @RequestHeader("Authorization") @Parameter(hidden = true) final String token,
            @RequestParam String nickName) {
        MemberNickNameResponseDto response = memberService.update(token, nickName);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "비밀번호 수정", description = "비밀번호를 수정하는 메서드입니다."+"\n\n### [ 참고사항 ]\n\n"+"- 카카오 소셜로그인의 경우 임의의 패스워드가 주어졌습니다. 이를 유저는 알 수 없으므로 클라이언트 단에서 비밀번호를 노출시킬 필요가 없습니다. \n\n")
    @CustomApi
    @PutMapping("/update/password")
    public ResponseEntity<Map<String, String>> updatePassword(
            @RequestHeader("Authorization") @Parameter(hidden = true) final String token,
            @RequestParam String password) {
        memberService.updatePassword(token, password);
        Map<String, String> response = new HashMap<>();
        response.put("message", "비밀번호 변경 성공");
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "회원탈퇴", description = "회원탈퇴 메서드입니다."+"\n\n### [ 참고사항 ]\n\n"+"- member의 state가 ACTIVE에서 RESIGNED으로 변경됩니다.\n\n")
    @CustomApi
    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, String>> delete(@RequestHeader("Authorization") @Parameter(hidden = true) final String token) {
        memberService.delete(token);
        Map<String, String> response = new HashMap<>();
        response.put("message", "회원탈퇴 성공");
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "모바일 홈 페이지", description = "모바일 홈페이지에 필요한 데이터를 불러오는 메서드입니다."+"\n\n### [ 참고사항 ]\n\n"+"- 반환값은 닉네임, 홈페이지에 필요한 전신 이미지입니다. \n\n")
    @CustomApi
    @GetMapping("/home")
    public ResponseEntity<MemberResponseDto> getHome(@RequestHeader("Authorization") @Parameter(hidden = true) final String token) {
//        Optional<Member> memberOptional = memberRepository.findById(id);
//
//        if (memberOptional.isPresent()) {
//            Member member = memberOptional.get();
//            MemberResponseDto response = new MemberResponseDto(member.getNickName(), member.getProfileImage());
//            return ResponseEntity.ok(response);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
        MemberResponseDto response = memberService.findMy(token);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "모바일 세팅 페이지", description = "모바일 세팅페이지에 필요한 데이터를 불러오는 메서드입니다."+"\n\n### [ 참고사항 ]\n\n"+"- 반환값은 닉네임, 세팅페이지에 필요한 상체 이미지입니다. \n\n")
    @CustomApi
    @GetMapping("/setting")
    public ResponseEntity<MemberSettingResponseDto> getSetting(@RequestHeader("Authorization") @Parameter(hidden = true) final String token) {
        MemberSettingResponseDto response = memberService.findMySet(token);
        return ResponseEntity.ok(response);

    }


    @Operation(summary = "서버용", description = "서버용 url")
    @PostMapping("/get/nickName")
    public String getNickname(@RequestParam Long memberId){
        return memberService.getNickname(memberId);
    }
}