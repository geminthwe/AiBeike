package com.lp.lpsystem.user.dto;

import lombok.Data;

@Data
public class LoginReq {
    private String name;
    private String email;
    private String password;
}
