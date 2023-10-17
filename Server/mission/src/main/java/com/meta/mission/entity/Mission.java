package com.meta.mission.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Table(name = "missions",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_missions_member_id_record_date",
                        columnNames = {"member_id", "recordDate"}
                )
        },
        indexes = @Index(name = "idx_missions_member_id_record_date",
                columnList = "member_id,recordDate"))
@Getter
@Entity
@NoArgsConstructor
@Setter
public class Mission {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long recordId;

    @Column(name = "member_id")
    private Long memberId;

    @Temporal(TemporalType.DATE)
    private Date recordDate;

    private String nickName;

    private int totalKcal;

    private int totalWalk;


    @Builder
    public Mission(Long memberId, Date recordDate, String nickName, int totalKcal, int totalWalk){
        this.memberId = memberId;
        this.recordDate = recordDate;
        this.nickName = nickName;
        this.totalKcal = totalKcal;
        this.totalWalk = totalWalk;
    }

}
