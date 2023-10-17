package com.meta.game.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameRankDto {
    private String nickName;
    private int winCount;

    public GameRankDto(String nickName, int winCount) {
        this.nickName = nickName;
        this.winCount = winCount;
    }
}
