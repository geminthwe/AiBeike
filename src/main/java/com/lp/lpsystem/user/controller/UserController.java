package com.lp.lpsystem.user.controller;

import com.lp.lpsystem.common.exception.http.BadRequestException;
import com.lp.lpsystem.user.context.UserInfoContext;
import com.lp.lpsystem.user.convert.UserConverter;
import com.lp.lpsystem.user.domain.UserDo;
import com.lp.lpsystem.user.dto.*;
import com.lp.lpsystem.user.service.UserService;
import com.lp.lpsystem.user.service.ValidCodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ValidCodeService ValidCodeService;

    @PostMapping("/register")
    public void createUser(@RequestBody CreateUserReq req) {
        if (StringUtils.isBlank(req.getEmail())) {
            throw new BadRequestException("email不能为空");
        }
        if (StringUtils.isBlank(req.getPassword())) {
            throw new BadRequestException("密码不能为空");
        }
        if (StringUtils.isBlank(req.getName())) {
            throw new BadRequestException("用户名不能为空");
        }

        UserDo o = UserConverter.req2do(req);
        userService.createUser(o);
    }

    @PostMapping("/login")
    public LoginResp login(@RequestBody LoginReq req) {

        return userService.login(req);
    }

    @GetMapping("/info")
    public UserDto getUserInfo() {
        UserDo userDo = UserInfoContext.getCurrentUser();
        // System.out.println("userDo: "+userDo);
        return UserConverter.do2dto(userDo);
    }

    @PutMapping("")
    public void updateUserInfo(@RequestBody UpdateUserReq req) {
        UserDo userDo = UserInfoContext.getCurrentUser();

        UserDo o = UserConverter.req2do(req);
        o.setId(userDo.getId());
        userService.updateUser(o);
    }

    @PutMapping("/password")
    public void updatePassword(@RequestBody UpdatePasswdReq req) {
        UserDo userDo = UserInfoContext.getCurrentUser();
        // System.out.println("pwd: " + userDo.getPassword());
        if (!userDo.getPassword().equals(req.getOldPassword())) {
            throw new BadRequestException("旧密码错误");
        }
        UserDo o = new UserDo();
        o.setId(userDo.getId());
        o.setPassword(req.getNewPassword());
        userService.updateUser(o);
    }


    @GetMapping("/validCode")
    public void verifyCode(@RequestParam String email) {
        if (StringUtils.isBlank(email)) {
            throw new BadRequestException("email不能为空");
        }
        ValidCodeService.send(email);
    }

    @PostMapping("/validCode/check")
    public void checkCode(@RequestBody CheckCodeReq req) {
        if (StringUtils.isBlank(req.getEmail())) {
            throw new BadRequestException("email不能为空");
        }
        if (StringUtils.isBlank(req.getCode())) {
            throw new BadRequestException("验证码不能为空");
        }
        if (!ValidCodeService.check(req.getEmail(), req.getCode())) {
            throw new BadRequestException("验证码错误");
        }
    }

    @PutMapping("/resetPassword")
    public void resetPassword(@RequestParam String newPwd) {
        UserDo userDo = UserInfoContext.getCurrentUser();
        UserDo o = new UserDo();
        o.setId(userDo.getId());
        o.setPassword(newPwd);
        userService.updateUser(o);
    }
}
