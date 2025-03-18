package com.lp.lpsystem.user.dto;

import lombok.Data;

@Data
public class CreateUserReq {
    private String email;
    private String name;
    private String password;
    private String gender;
    private String qq;
    private String mobile;
}
