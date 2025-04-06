package com.lp.lpsystem.call.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class LessonPlan {
    @JsonProperty("教学主题")
    private String teachingTopic;

    @JsonProperty("年级")
    private String gradeLevel;

    @JsonProperty("学生水平")
    private String studentLevel;

    @JsonProperty("教学目标")
    private TeachingObjectives objectives;

    @JsonProperty("教学流程")
    private TeachingProcess process;

    @JsonProperty("差异化策略")
    private DifferentiationStrategy differentiationStrategy;

    @JsonProperty("评估方案")
    private List<String> assessmentPlans;

    private String planId;
}
