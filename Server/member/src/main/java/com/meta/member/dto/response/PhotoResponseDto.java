package com.meta.member.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PhotoResponseDto {
    private Long memberId;
    private String profileImage;
    private String settingImage;
}
