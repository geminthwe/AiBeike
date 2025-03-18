package com.lp.lpsystem.user.domain;

import lombok.Data;

@Data
public class UserDo {
    private Integer id;
    private String email;
    private String name;
    private String password;
    private String avatar;
    private String gender;
    private String qq;
    private String mobile;
    private String subject;
    private String grade;
}
