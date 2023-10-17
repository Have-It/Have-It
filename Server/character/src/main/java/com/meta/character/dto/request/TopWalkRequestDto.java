package com.meta.character.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TopWalkRequestDto {
    private int maxWalk;
    private List<String> nickNames;
    private List<Long> memberIds;
}
