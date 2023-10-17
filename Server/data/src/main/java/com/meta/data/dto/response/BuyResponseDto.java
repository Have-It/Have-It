package com.meta.data.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyResponseDto {
    private int remainCoin;
    public BuyResponseDto(int remainCoin) {
        this.remainCoin = remainCoin;
    }
}
