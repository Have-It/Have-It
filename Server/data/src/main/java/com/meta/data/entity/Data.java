package com.meta.data.entity;

import lombok.*;
import javax.persistence.*;

@lombok.Data
@Entity(name = "datas")
@NoArgsConstructor
@Setter
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String nickName;

    private int totalSuccess;
    private int coin;
    private int characterWeight;
    private int characterDarkcircle;
    private boolean dailyPet;
    private int effectWeight;
    private int effectDarkcircle;
    private boolean mission1Success;
    private boolean mission2Success;
    private boolean mission3Success;

    @Builder
    public Data(Long memberId, String nickName,  int effectWeight,int effectDarkcircle, int totalSuccess, int coin, int characterWeight, int characterDarkcircle,
                 boolean dailyPet,boolean mission1Success, boolean mission2Success, boolean mission3Success){

        this.id = memberId;
        this.effectWeight=effectWeight;
        this.effectDarkcircle=effectDarkcircle;
        this.totalSuccess = totalSuccess;
        this.nickName = nickName;
        this.coin = coin;
        this.dailyPet = dailyPet;
        this.characterWeight = characterWeight;
        this.characterDarkcircle = characterDarkcircle;
        this.mission1Success = mission1Success;
        this.mission2Success = mission2Success;
        this.mission3Success = mission3Success;

    }

}
