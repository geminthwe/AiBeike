package com.lp.lpsystem.call.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DifferentiationStrategy {

    @JsonProperty("进阶挑战")
    private String advancedChallenge;

    @JsonProperty("支持方案")
    private String supportPlan;
}
