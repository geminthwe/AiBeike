package com.lp.lpsystem.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value ="User")
@Data
public class User {
    @TableId
    private Integer id;
    @TableField("Email")
    private String email;
    @TableField("Name")
    private String name;
    @TableField("Password")
    private String password;
    @TableField("Avatar")
    private String avatar;
    @TableField("Gender")
    private String gender;
    @TableField("QQ")
    private String qq;
    @TableField("Mobile")
    private String mobile;
    @TableField("Subject")
    private String subject;
    @TableField("Grade")
    private String grade;
}
