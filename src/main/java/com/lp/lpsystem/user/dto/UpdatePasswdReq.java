package com.lp.lpsystem.user.dto;

import lombok.Data;

@Data
public class UpdatePasswdReq {
    private String oldPassword;
    private String newPassword;
}
