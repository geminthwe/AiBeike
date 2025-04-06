package com.lp.lpsystem.call.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TeachingObjectives {

    @JsonProperty("知识目标")
    private List<String> knowledgeObjectives;

    @JsonProperty("能力目标")
    private String abilityObjective;

    @JsonProperty("情感目标")
    private String emotionalObjective;
}
