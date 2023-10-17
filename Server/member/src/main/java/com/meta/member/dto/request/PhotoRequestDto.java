package com.meta.member.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhotoRequestDto {
    private Long memberId;
    private String base64Image;
    private String bodyType;
}
