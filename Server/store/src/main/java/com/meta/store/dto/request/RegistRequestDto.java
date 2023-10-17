package com.meta.store.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegistRequestDto {
    private String type;
    private Long typeId;
    private int price;
}
