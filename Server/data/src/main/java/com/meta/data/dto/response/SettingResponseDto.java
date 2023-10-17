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
public class SettingResponseDto {
//    private String nickname;
//    private String profileImage;
    private int characterWeight;
    private int characterDarkcircle;

    public SettingResponseDto(Data userData) {
//        this.nickname = nickname;
//        this.profileImage = profileImage;
        this.characterDarkcircle = userData.getCharacterDarkcircle();
        this.characterWeight = userData.getCharacterWeight();
    }
}