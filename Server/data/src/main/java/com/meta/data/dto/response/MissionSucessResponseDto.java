package com.meta.data.dto.response;

import com.meta.data.entity.Data;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MissionSucessResponseDto {
    private String message;
    private boolean mission1Success;
    private boolean mission2Success;
    private boolean mission3Success;
    private int coin;

    public MissionSucessResponseDto(Data data) {
        this.message = "post 요청이 성공하였습니다.";
        this.coin = data.getCoin();
        this.mission1Success = data.isMission1Success();
        this.mission2Success = data.isMission2Success();
        this.mission3Success = data.isMission3Success();
    }

}
