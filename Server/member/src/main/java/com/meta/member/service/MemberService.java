package com.meta.member.service;


import com.meta.member.component.DataClientComponent;
import com.meta.member.component.GameClientComponent;
import com.meta.member.component.MissionClientComponent;
import com.meta.member.dto.request.*;
import com.meta.member.dto.response.*;
import com.meta.member.enums.MemberState;
import com.meta.member.exception.BusinessException;
import com.meta.member.exception.DuplicatedMemberException;
import com.meta.member.exception.EmptyValueException;
import com.meta.member.exception.NoSuchMemberException;
import com.meta.member.entity.Member;
import com.meta.member.exception.enums.ErrorCode;
import com.meta.member.repository.MemberRepository;
import com.meta.member.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;


import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final StringRedisTemplate stringRedisTemplate;
    private final WebClient webClient = WebClient.create();
    private final DataClientComponent dataClientComponent;
    private final MissionClientComponent missionClientComponent;
    private final PhotoService photoService;
    private final GameClientComponent gameClientComponent;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    String kakaoClientId;
    @Value("${spring.security.oauth2.client.registration.kakao.redirectUri}")
    String kakaoRedirectUrl;
    @Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
    String grantType;

    @Transactional(readOnly = true)
    public Member findMember(String key, String value) {
        if(key.equals("id")) {
            return memberRepository.findById(Long.valueOf(value)).orElseThrow(NoSuchMemberException::new);
        } else if(key.equals("email")) {
            return memberRepository.findByEmail(value).orElseThrow(NoSuchMemberException::new);
        } else {
            throw new NoSuchMemberException();
        }
    }

    @Transactional
    public void signUp(MemberSignupRequestDto memberSignupRequestDto) {
        if (memberRepository.existsByEmail(memberSignupRequestDto.getEmail())) {
            throw new DuplicatedMemberException();
        }

        if (memberRepository.existsByNickName(memberSignupRequestDto.getNickName())) {
            throw new DuplicatedMemberException("중복된 닉네임입니다.");
        }

        Member member = Member.builder()
                .email(memberSignupRequestDto.getEmail())
                .password(bCryptPasswordEncoder.encode(memberSignupRequestDto.getPassword()))
                .nickName(memberSignupRequestDto.getNickName())
                .build();

        member = memberRepository.save(member);

        try {
            byte[] defaultProfileImageBytes = new ClassPathResource("static/firstProfileImage.png").getInputStream().readAllBytes();
            byte[] defaultSettingImageBytes = new ClassPathResource("static/settingImage.png").getInputStream().readAllBytes();

            String profileImageUrl = photoService.uploadPhoto(member.getId(), Base64.getEncoder().encodeToString(defaultProfileImageBytes), "fullBody");
            String settingImageUrl = photoService.uploadPhoto(member.getId(), Base64.getEncoder().encodeToString(defaultSettingImageBytes), "halfBody");

            member.setProfileImage(profileImageUrl);
            member.setSettingImage(settingImageUrl);

        } catch (IOException e) {
            throw new BusinessException(ErrorCode.FAIL_SAVE_IMAGE);
        }

        memberRepository.save(member);


        try {
            DataCreateRequestDto dataCreateRequestDto = new DataCreateRequestDto();
            dataCreateRequestDto.setMemberId(member.getId());
            dataCreateRequestDto.setNickName(member.getNickName());
            ResponseEntity<String> responseEntity = dataClientComponent.createData(dataCreateRequestDto);

            if (!"data DB Success".equals(responseEntity.getBody())) {
                throw new RuntimeException("Failed to create data db for member.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            MissionCreateRequestDto missionCreateRequestDto = new MissionCreateRequestDto();
            missionCreateRequestDto.setMemberId(member.getId());
            Date today = new Date();
            missionCreateRequestDto.setRecordDate(today);
            missionCreateRequestDto.setNickName(member.getNickName());
            ResponseEntity<String> responseEntity = missionClientComponent.creatMission(missionCreateRequestDto);

            if (!"mission DB Success".equals(responseEntity.getBody())) {
                throw new RuntimeException("Failed to create mission db for member.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public MessageResponseDto checkEmail(String email) {
        String res = "";
        String msg = "";
        if (memberRepository.existsByEmail(email)) {
            res = "fail";
            msg = "중복된 이메일입니다.";
        } else {
            res = "success";
            msg = "사용 가능한 이메일입니다.";
        }
        MessageResponseDto message = MessageResponseDto.builder()
                .result(res)
                .message(msg)
                .build();
        return message;
    }

    @Transactional(readOnly = true)
    public MessageResponseDto checkNickName(String nickname) {
        String res = "";
        String msg = "";
        if (memberRepository.existsByNickName(nickname)) {
            res = "fail";
            msg = "중복된 닉네임입니다.";
        } else {
            res = "success";
            msg = "사용 가능한 닉네임입니다.";
        }
        MessageResponseDto message = MessageResponseDto.builder()
                .result(res)
                .message(msg)
                .build();
        return message;
    }

    @Transactional
    public MemberTokenResponseDto login(MemberLoginRequestDto memberLoginRequestDto, HttpServletRequest request) {
        Member member = findMember("email", memberLoginRequestDto.getEmail());
        if (member.getState().equals(MemberState.RESIGNED.name())) throw new NoSuchMemberException("탈퇴한 사용자입니다.");

        String secChUaPlatform = request.getHeader("Sec-Ch-Ua-Platform");

        UsernamePasswordAuthenticationToken authenticationToken = memberLoginRequestDto.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        MemberTokenResponseDto tokenInfo = jwtTokenProvider.generateToken(authentication, member, secChUaPlatform);

        stringRedisTemplate.opsForValue()
                .set("RT:" + tokenInfo.getAccessToken(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return tokenInfo;
    }


    @Transactional
    public MemberTokenResponseDto loginOauth2Kakao(String token, HttpServletRequest request) throws IOException {
        log.info("loginOauth2Kakao 로 들어옴");
        Map<String, Object> responseBody = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("kapi.kakao.com")
                        .path("/v2/user/me")
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();

        String subValue = responseBody.get("id").toString();
        Map<String, Object> kakaoAcount = (Map<String, Object>) responseBody.get("kakao_account");
        String emailValue = kakaoAcount.get("email").toString();
        Map<String, Object> profile = (Map<String, Object>) kakaoAcount.get("profile");
        String nameValue = profile.get("nickname").toString();

        return oauth2Login("kakao", emailValue, nameValue, request);
    }

    @Transactional
    public MemberTokenResponseDto oauth2Login(String socialtype, String emailValue, String nameValue, HttpServletRequest request) throws IOException {
        log.info("oauth2Login 로 들어옴");
        // 다음 형태의 이메일로 이미 가입 되어있는지 확인
        String email = socialtype+"@"+emailValue;   // ex)google@ejk9658@gmail.com
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        Member member;
        if (optionalMember.isEmpty()) {
            // 해당 닉네임으로 이미 가입 되어있는지 확인함
            int num = 0;
            String name = nameValue;
            while(memberRepository.existsByNickName(name)) {
                name = nameValue+"_"+(++num);    // ex)닉네임_1
            }

            // 회원가입 진행
            member = Member
                    .builder()
                    .email(email)
                    .password(bCryptPasswordEncoder.encode("카카오임시혜원"))
                    .nickName(name)
                    .build();

            member = memberRepository.save(member);

            try {
                byte[] defaultProfileImageBytes = new ClassPathResource("static/firstProfileImage.png").getInputStream().readAllBytes();
                byte[] defaultSettingImageBytes = new ClassPathResource("static/settingImage.png").getInputStream().readAllBytes();

                String profileImageUrl = photoService.uploadPhoto(member.getId(), Base64.getEncoder().encodeToString(defaultProfileImageBytes), "fullBody");
                String settingImageUrl = photoService.uploadPhoto(member.getId(), Base64.getEncoder().encodeToString(defaultSettingImageBytes), "halfBody");

                member.setProfileImage(profileImageUrl);
                member.setSettingImage(settingImageUrl);

            } catch (IOException e) {
                throw new BusinessException(ErrorCode.FAIL_SAVE_IMAGE);
            }

            memberRepository.save(member);

            try {
                DataCreateRequestDto dataCreateRequestDto = new DataCreateRequestDto();
                dataCreateRequestDto.setMemberId(member.getId());
                dataCreateRequestDto.setNickName(member.getNickName());
                ResponseEntity<String> responseEntity = dataClientComponent.createData(dataCreateRequestDto);

                if (!"data DB Success".equals(responseEntity.getBody())) {
                    throw new RuntimeException("data db를 만드는데 오류가나서 회원가입 불가능");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            try {
                MissionCreateRequestDto missionCreateRequestDto = new MissionCreateRequestDto();
                missionCreateRequestDto.setMemberId(member.getId());
                Date today = new Date();
                missionCreateRequestDto.setRecordDate(today);
                missionCreateRequestDto.setNickName(member.getNickName());
                ResponseEntity<String> responseEntity = missionClientComponent.creatMission(missionCreateRequestDto);

                if (!"mission DB Success".equals(responseEntity.getBody())) {
                    throw new RuntimeException("Failed to create mission db for member.");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        } else {
            member = optionalMember.get();
        }

        MemberLoginRequestDto loginDto = new MemberLoginRequestDto();
        loginDto.setEmail(member.getEmail());
        loginDto.setPassword("카카오임시혜원");

        return login(loginDto, request);
    }


    public void logout(String token) {
        updateRedisItems(token, "logout");
    }

    @Transactional(readOnly = true)
    public MemberMyPageResponseDto findMyPage(String token) {
        Member member = findMember("id", jwtTokenProvider.getMember(token));
        return new MemberMyPageResponseDto().fromMeEntity(member);
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findMy(String token) {
        Member member = findMember("id", jwtTokenProvider.getMember(token));
        return new MemberResponseDto(member.getNickName(),member.getProfileImage());
    }

    @Transactional(readOnly = true)
    public MemberSettingResponseDto findMySet(String token){
        Member member = findMember("id", jwtTokenProvider.getMember(token));
        return new MemberSettingResponseDto(member.getNickName(), member.getSettingImage());
    }


    @Transactional
    public MemberNickNameResponseDto update(String token, String nickName) {
        Member member = findMember("id", jwtTokenProvider.getMember(token));

        if ("".equals(nickName)) {    // 닉네임 공백 체크
            throw new NoSuchMemberException("닉네임은 필수 입력 사항입니다.");
        }
        if (!member.getNickName().equals(nickName) &&
                memberRepository.existsByNickName(nickName)) { // 닉네임 중복체크
            throw new DuplicatedMemberException("중복된 닉네임입니다.");
        }
        member.setNickName(nickName);
        dataClientComponent.update(member.getId(), nickName);
        missionClientComponent.update(member.getId(), nickName);
        gameClientComponent.update(member.getId(), nickName);
        return new MemberNickNameResponseDto().fromMeEntity(member);
    }


    @Transactional
    public void updatePassword(String token, String password) {
        Member member = findMember("id", jwtTokenProvider.getMember(token));

        if("".equals(password)) {
            throw new EmptyValueException("변경할 비밀번호를 입력해주세요.");
        }
        if (member.getSocialtype() == null) {
            member.setPassword(bCryptPasswordEncoder.encode(password));
        }
    }

    @Transactional
    public void delete(String token) {
        Member member = findMember("id", jwtTokenProvider.getMember(token));
        member.setState(MemberState.RESIGNED.name());

        updateRedisItems(token, "resign");
    }

    @Transactional
    public void updateRedisItems(String token, String status) {
        String accessToken = token.split(" ")[1];

        // 1. Redis Access Token 으로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제합니다.
        // "RT:{AccessToken}" : "{RefreshToken}"
        if (stringRedisTemplate.opsForValue().get("RT:" + accessToken) != null) {
            // Refresh Token 삭제
            stringRedisTemplate.delete("RT:" + accessToken);
        }

        // 2. 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
        Long expiration = jwtTokenProvider.getExpiration(accessToken);
        stringRedisTemplate.opsForValue().set(accessToken, status, expiration, TimeUnit.MILLISECONDS);
    }

    @Transactional
    public String getNickname(Long memberId){
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isEmpty()){
            throw new BusinessException(ErrorCode.NOT_FOUND_MEMBER);
        }
        return optionalMember.get().getNickName();
    }
}
