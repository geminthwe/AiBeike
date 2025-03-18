package com.lp.lpsystem.user.dto;

import lombok.Data;

@Data
public class UpdateUserReq {
    private String name;
    private String gender;
    private String mobile;
    private String email;
    private String qq;
    private String subject;
    private String grade;
}
