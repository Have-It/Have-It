package com.meta.member.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberSettingResponseDto {
    private String nickname;
    private String settingImage;
    public MemberSettingResponseDto(String nickname, String settingImage) {
        this.nickname = nickname;
        this.settingImage = settingImage;
    }
}
