package com.meta.mission.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TopWalkResultDto {
    private int maxWalk;
    private List<String> nickNames;
    private List<Long> memberIds;
}
