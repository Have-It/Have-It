package com.meta.character.dto.request;

import com.meta.character.dto.response.CharacterInfoResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TopSuccessRequestDto {
    private int maxSuccessDay;
    private List<String> nickNames;
    private List<Long> memberIds;

}
