package com.meta.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SelfyResponseDto {
    private Long memberId;
    private Long photoId;
    private String imageUrl;
}