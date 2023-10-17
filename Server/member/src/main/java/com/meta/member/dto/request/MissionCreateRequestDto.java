package com.meta.member.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
public class MissionCreateRequestDto {
    private Long memberId;

    @Temporal(TemporalType.DATE)
    private Date recordDate;

    private String nickName;

    private int totalKcal;

    private int totalWalk;

    private int goalKcal;

    private boolean exerciseSuccess;

    private int sleepTime;

    private boolean sleepSuccess;

    private String customMissionType;

    private String customMissionDescript;

    private boolean customMissionSuccess;

}
