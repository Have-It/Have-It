package com.meta.member.dto.response;


import com.meta.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberMyPageResponseDto {
    private String email;
    private String nickName;
    private String profileImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String socialtype;
    private String settingImage;
//    private int characterWeight;
//    private int characterDarkcircle;
//    private int coin;


    public MemberMyPageResponseDto fromMeEntity(Member member) {
        return MemberMyPageResponseDto.builder()
                .email(member.getEmail())
                .nickName(member.getNickName())
                .socialtype(member.getSocialtype())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .profileImage(member.getProfileImage())
                .settingImage(member.getSettingImage())
//                .characterWeight(member.getCharacterWeight())
//                .characterDarkcircle(member.getCharacterDarkcircle())
//                .coin(member.getCoin())
                .build();
    }

}
