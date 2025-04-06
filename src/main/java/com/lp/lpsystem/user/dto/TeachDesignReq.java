package com.lp.lpsystem.user.dto;

import lombok.Data;

@Data
public class TeachDesignReq {
    private String theme;// 主题
    private String student_level;// 学生水平
    private String class_duration;// 时长
    private String subject;// 科目
    private String grade;// 年级
    private String teaching_style;// 教学风格
    private String special_needs;// 特殊需求
}
