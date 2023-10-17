package com.meta.character.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity(name = "characters")
@Getter
@NoArgsConstructor
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private Long memberId;

    private int hair;
    private int acc;
    private int top;
    private int bottom;
    private int shoes;
    private int pets;
    private int face;


    @Builder
    public Character(Long id, Long memberId, int hair, int acc, int top, int bottom, int shoes, int pets, int face){
        this.id = id;
        this.memberId = memberId;
        this.acc = acc;
        this.hair = hair;
        this.bottom = bottom;
        this.face = face;
        this.pets = pets;
        this.top = top;
        this.shoes = shoes;
    }

}
