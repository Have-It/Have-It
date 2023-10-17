package com.meta.mission.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RankRecordRequestDto {
    private int totalKcal;
    private int totalWalk;
}
