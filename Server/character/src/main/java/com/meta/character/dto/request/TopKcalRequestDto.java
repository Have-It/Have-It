package com.meta.character.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TopKcalRequestDto {
    private int maxKcal;
    private List<String> nickNames;
    private List<Long> memberIds;
}
