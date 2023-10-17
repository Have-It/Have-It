package com.meta.game.dto.response;

import com.meta.game.entity.GameResult;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameResultResponseDto {
    private Long memberId;
    private int winCount;
    private String nickName;
    public GameResultResponseDto(GameResult gameResult) {
        this.memberId = gameResult.getMemberId();
        this.winCount = gameResult.getWinCount();
        this.nickName = gameResult.getNickName();
    }
}
