package com.lp.lpsystem.call.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TeachingProcess {

    @JsonProperty("总时长")
    private String totalTime;

    @JsonProperty("环节")
    private List<ProcessStep> steps;
}
