package com.meta.member.controller;

import com.meta.member.dto.request.PhotoRequestDto;
import com.meta.member.dto.request.SelfyRequestDto;
import com.meta.member.dto.response.PhotoResponseDto;
import com.meta.member.dto.response.SelfyResponseDto;
import com.meta.member.entity.Member;
import com.meta.member.global.CustomApi;
import com.meta.member.jwt.JwtTokenProvider;
import com.meta.member.service.MemberService;
import com.meta.member.service.PhotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class PhotoController {

    private final PhotoService photoService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;


    @Operation(summary = "인벤토리 사진 저장", description = "인벤토리에서 나오면서 사진 저장하는 메서드입니다."+"\n\n### [ 참고사항 ]\n\n"+"- bodyType은 fullBody 또는 halfBody 값으로 넣어주세요")
    @CustomApi
    @PostMapping("/uploadPhoto")
    public ResponseEntity<PhotoResponseDto> uploadPhoto(@org.springframework.web.bind.annotation.RequestBody PhotoRequestDto photoRequestDto) {
        String imageURL = photoService.uploadPhoto(photoRequestDto.getMemberId(), photoRequestDto.getBase64Image(), photoRequestDto.getBodyType());

        PhotoResponseDto response = new PhotoResponseDto();
        response.setMemberId(photoRequestDto.getMemberId());

        if (photoRequestDto.getBodyType().equals("fullBody")) {
            response.setProfileImage(imageURL);
            photoService.updateProfileImage(photoRequestDto.getMemberId(), imageURL);  // profile 이미지만 업데이트
        } else if (photoRequestDto.getBodyType().equals("halfBody")) {
            response.setSettingImage(imageURL);
            photoService.updateSettingImage(photoRequestDto.getMemberId(), imageURL);  // setting 이미지만 업데이트
        }

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "셀카 사진 저장", description = "셀카 사진 저장하는 메서드입니다.")
    @CustomApi
    @PostMapping("/selfy")
    public ResponseEntity<SelfyResponseDto> uploadSelfy(@RequestHeader("Authorization") @Parameter(hidden = true) String token,
                                                        @RequestBody SelfyRequestDto selfieRequestDTO){

        Member member=memberService.findMember("id",jwtTokenProvider.getMember(token));
        Long memberId=member.getId();

        SelfyResponseDto response=photoService.uploadSelfy(memberId,selfieRequestDTO.getBase64Image());

        return ResponseEntity.ok(response);
    }



    @Operation(summary = "셀카 사진 조회", description = "유저가 찍은 모든 셀카 사진들을 리스트로 반환하는 메서드입니다.")
    @CustomApi
    @GetMapping("/selfies")
    public ResponseEntity<List<SelfyResponseDto>> getSelfies(@RequestHeader("Authorization") @Parameter(hidden = true) String token){

        Member member=memberService.findMember("id",jwtTokenProvider.getMember(token));
        Long memberId=member.getId();

        List<SelfyResponseDto> responses=photoService.getAllPhotos(memberId);

        return ResponseEntity.ok(responses);
    }

//    @Operation(summary = "셀카 사진 삭제", description = "셀카 사진 삭제하는 메서드입니다."+"\n\n### [ 참고사항 ]\n\n"+"- url에 몇번째 사진을 삭제할 지 함꼐 작성해서 보내주시면 됩니다. 자기가 올린사진이 아니면 500에러 뜹니다.")
//    @CustomApi
//    @DeleteMapping("/selfie/{photoId}")
//    public String deleteSelfie(@PathVariable Long photoId, @RequestHeader("Authorization") @Parameter(hidden = true) String token){
//
//        Member member = memberService.findMember("id", jwtTokenProvider.getMember(token));
//        Long memberId = member.getId();
//
//        photoService.deleteSelfy(memberId,photoId);
//
//        return photoId+" 번째 사진 삭제 성공!";
//    }
}
