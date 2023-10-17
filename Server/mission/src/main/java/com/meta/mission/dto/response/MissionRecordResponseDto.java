package com.meta.mission.dto.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class MissionRecordResponseDto {
    private Long id;
    private Date recordDate;
    private int sleepTime;
    private String customMissionType;
    private String customMissionDescript;

}
