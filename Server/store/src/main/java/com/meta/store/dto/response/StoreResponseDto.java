package com.meta.store.dto.response;

import com.meta.store.entity.Store;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class StoreResponseDto {
    private Long typeId;
    private int price;
    private boolean owned;

    public StoreResponseDto(Long typeId, int price, boolean owned) {
        this.typeId = typeId;
        this.price = price;
        this.owned = owned;
    }
}
