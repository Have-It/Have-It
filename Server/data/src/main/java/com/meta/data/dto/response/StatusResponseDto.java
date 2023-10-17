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
public class StatusResponseDto {
    private int coin;
    private int characterWeight;
    private int characterDarkcircle;
    private boolean dailyPet;

    public StatusResponseDto(Data userData) {
        this.coin = userData.getCoin();
        this.characterDarkcircle = userData.getCharacterDarkcircle();
        this.characterWeight = userData.getCharacterWeight();
        this.dailyPet = userData.isDailyPet();
    }
}
