package com.lp.lpsystem.user.dto;

import lombok.Data;

@Data
public class CheckCodeReq {
    String code;
    String email;
}
