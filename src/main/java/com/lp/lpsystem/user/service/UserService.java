package com.lp.lpsystem.user.service;

import com.lp.lpsystem.user.domain.UserDo;
import com.lp.lpsystem.user.dto.LoginReq;
import com.lp.lpsystem.user.dto.LoginResp;
import com.lp.lpsystem.user.service.impl.UserServiceImpl;

public interface UserService {
    void createUser(UserDo o);
    LoginResp login(LoginReq req);

    UserDo getUser(UserServiceImpl.QueryParam param);

    UserDo getUserByName(String userName);

    void updateUser(UserDo o);
}
