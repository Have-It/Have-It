package com.meta.mission.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TopKcalResultDto {
    private int maxKcal;
    private List<String> nickNames;
    private List<Long> memberIds;
}
