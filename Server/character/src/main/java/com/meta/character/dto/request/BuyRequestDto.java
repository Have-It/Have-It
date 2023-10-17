package com.meta.character.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BuyRequestDto {
    private String type;
    private Long typeId;
    private int price;
}
