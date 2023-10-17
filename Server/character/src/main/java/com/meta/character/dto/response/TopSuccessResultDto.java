package com.meta.character.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TopSuccessResultDto {
    private int maxSuccessDay;
    private List<String> nickNames;
    private List<CharacterInfoResponseDto> characterInfos;
}
