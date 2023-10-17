package com.meta.member.dto.response;

import com.meta.member.entity.Member;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberNickNameResponseDto {
    private String nickName;
    public MemberNickNameResponseDto fromMeEntity(Member member) {
        return MemberNickNameResponseDto.builder()
                .nickName(member.getNickName())
                .build();
    }
}
