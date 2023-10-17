package com.meta.data.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DataCreateRequestDto {
    private Long memberId;
    private String nickName;
    private int effectWeight;
    private int effectDarkcircle;
    private int totalSuccess;
    private int coin;
    private int characterWeight = 12;
    private int characterDarkcircle = 12;
    private boolean mission1Success;
    private boolean mission2Success;
    private boolean mission3Success;
}
