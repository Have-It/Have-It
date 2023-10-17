package com.meta.character.dto.response;

import com.meta.character.entity.Character;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CharacterInfoResponseDto {
    private Long memberId;
    private int hair;
    private int acc;
    private int top;
    private int bottom;
    private int shoes;
    private int pets;
    private int face;

    public static CharacterInfoResponseDto from(Character character) {
        CharacterInfoResponseDto dto = new CharacterInfoResponseDto();
        dto.memberId = character.getMemberId();
        dto.hair = character.getHair();
        dto.acc = character.getAcc();
        dto.top = character.getTop();
        dto.bottom = character.getBottom();
        dto.shoes = character.getShoes();
        dto.pets = character.getPets();
        dto.face = character.getFace();

        return dto;
    }

}
