package com.meta.mission.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MissionRecordRequestDto {
    private int sleepTime;
    private String customMissionType;
    private String customMissionDescript;
}
