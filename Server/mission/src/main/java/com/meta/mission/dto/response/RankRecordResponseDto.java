package com.meta.mission.dto.response;

import com.meta.mission.dto.request.RankRecordRequestDto;
import com.meta.mission.entity.Mission;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class RankRecordResponseDto {
    private Long memberId;
    private Date recordDate;
    private int totalKcal;
    private int totalWalk;

    public RankRecordResponseDto(Mission mission){
        this.memberId = mission.getMemberId();
        this.recordDate = mission.getRecordDate();
        this.totalKcal = mission.getTotalKcal();
        this.totalWalk = mission.getTotalWalk();
    }
}
