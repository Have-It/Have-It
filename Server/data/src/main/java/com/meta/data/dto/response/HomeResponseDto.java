package com.meta.data.dto.response;

import com.meta.data.entity.Data;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HomeResponseDto {
    private int totalSuccess;
    private int coin;
    private boolean mission1Success;
    private boolean mission2Success;
    private boolean mission3Success;
//    private String nickname;
//    private String profileImage;

    public HomeResponseDto(Data userData) {
        this.totalSuccess = userData.getTotalSuccess();
        this.coin = userData.getCoin();
        this.mission1Success = userData.isMission1Success();
        this.mission2Success = userData.isMission2Success();
        this.mission3Success = userData.isMission3Success(); // 수정된 부분
//        this.nickname = nickname;
//        this.profileImage = profileImage;
    }
}
