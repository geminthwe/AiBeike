package com.lp.lpsystem.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lp.lpsystem.common.exception.http.BadRequestException;
import com.lp.lpsystem.common.utils.JwtUtil;
import com.lp.lpsystem.user.convert.UserConverter;
import com.lp.lpsystem.user.dao.UserMapper;
import com.lp.lpsystem.user.domain.UserDo;
import com.lp.lpsystem.user.dto.LoginReq;
import com.lp.lpsystem.user.dto.LoginResp;
import com.lp.lpsystem.user.dto.UserDto;
import com.lp.lpsystem.user.entity.User;
import com.lp.lpsystem.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void createUser(UserDo o) {
        try {
            User e = UserConverter.do2e(o);
            userMapper.insert(e);
        } catch (DuplicateKeyException ex) {
            String errorMessage = ex.getCause().getMessage();
            // System.out.println("errorMessage: " + errorMessage);
            if (errorMessage.contains("user.UK_Name")) {  // 根据实际情况调整
                throw new BadRequestException("用户名已存在，请选择其他用户名");
            } else if (errorMessage.contains("user.UK_Email")) {  // 如果还有其他唯一约束
                throw new BadRequestException("邮箱已存在，请选择其他邮箱");
            } else {
                // 其他类型的唯一约束冲突
                throw new RuntimeException("数据插入失败: " + errorMessage);
            }
        }
    }

    @Override
    public LoginResp login(LoginReq req) {
        QueryParam param = UserConverter.req2param(req);
        // System.out.println("param: "+param);
        UserDo userDo = getUser(param);
        if (userDo == null) {
            throw new BadRequestException("用户不存在");
        }
        if (!userDo.getPassword().equals(req.getPassword())) {
            throw new BadRequestException("密码错误");
        }
        UserDto dto = UserConverter.do2dto(userDo);
        String token = JwtUtil.createTokenByUserName(userDo.getName());
        return new LoginResp(dto, token);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class QueryParam {
        String name;
        String email;
    }

    @Override
    public UserDo getUser(QueryParam param) {
        LambdaQueryWrapper<User> wrapper = prepareQuery(param);
        // System.out.println("wrapper: "+wrapper);
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            // System.out.println("user is null");
            return null;
        }
        return UserConverter.e2do(user);
    }

    @Override
    public UserDo getUserByName(String userName) {
        LambdaQueryWrapper<User> wrapper = prepareQuery(new QueryParam());
        wrapper.eq(User::getName, userName);
        User user = userMapper.selectOne(wrapper);
        return user == null ? null : UserConverter.e2do(user);
    }

    @Override
    public void updateUser(UserDo o) {
        try {
            userMapper.updateById(UserConverter.do2e(o));
        } catch (DuplicateKeyException ex) {
            String errorMessage = ex.getCause().getMessage();
            // System.out.println("errorMessage: " + errorMessage);
            if (errorMessage.contains("user.UK_Name")) {
                throw new BadRequestException("用户名已存在，请选择其他用户名");
            } else if (errorMessage.contains("user.UK_Email")) {
                throw new BadRequestException("邮箱已存在，请选择其他邮箱");
            } else {
                throw new RuntimeException("数据更新失败: " + errorMessage);
            }
        }
    }

    private LambdaQueryWrapper<User> prepareQuery(QueryParam param) {
        if (param == null) {
            throw new IllegalArgumentException("Invalid param passed: null");
        }

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNoneBlank(param.name)) {
            wrapper.eq(User::getName, param.name);
        }
        if (StringUtils.isNoneBlank(param.email)) {
            wrapper.eq(User::getEmail, param.email);
        }
        return wrapper;
    }
}
