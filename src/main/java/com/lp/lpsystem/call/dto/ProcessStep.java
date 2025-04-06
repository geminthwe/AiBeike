package com.lp.lpsystem.call.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProcessStep {

    @JsonProperty("名称")
    private String name;

    @JsonProperty("时长")
    private String duration;

    @JsonProperty("内容")
    private String content;

    @JsonProperty("互动类型")
    private List<String> interactionTypes;
}
