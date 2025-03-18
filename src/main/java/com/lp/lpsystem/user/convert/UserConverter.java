package com.lp.lpsystem.user.convert;

import com.lp.lpsystem.common.utils.PasswdUtil;
import com.lp.lpsystem.user.dto.CreateUserReq;
import com.lp.lpsystem.user.dto.LoginReq;
import com.lp.lpsystem.user.dto.UpdateUserReq;
import com.lp.lpsystem.user.dto.UserDto;
import com.lp.lpsystem.user.domain.UserDo;
import com.lp.lpsystem.user.entity.User;
import com.lp.lpsystem.user.service.impl.UserServiceImpl;

public class UserConverter {

    // Dto -> Do -> Entity

    /**
     * Convert UserDo to UserDto
     *
     * @param userDo the source object
     * @return UserDto
     */
    public static UserDto do2dto(UserDo userDo) {
        UserDto userDto = new UserDto();
        userDto.setEmail(userDo.getEmail());
        userDto.setName(userDo.getName());
        userDto.setAvatar(userDo.getAvatar());
        userDto.setGender(userDo.getGender());
        userDto.setQq(userDo.getQq());
        userDto.setMobile(userDo.getMobile());
        userDto.setSubject(userDo.getSubject());
        userDto.setGrade(userDo.getGrade());
        return userDto;
    }

    /**
     * Convert UserDo to User (Entity)
     *
     * @param userDo the source object
     * @return User
     */
    public static User do2e(UserDo userDo) {
        User user = new User();
        user.setId(userDo.getId());
        user.setEmail(userDo.getEmail());
        user.setName(userDo.getName());
        String encoded = PasswdUtil.encode(userDo.getPassword());
        user.setPassword(encoded);
        user.setAvatar(userDo.getAvatar());
        user.setGender(userDo.getGender());
        user.setQq(userDo.getQq());
        user.setMobile(userDo.getMobile());
        user.setSubject(userDo.getSubject());
        user.setGrade(userDo.getGrade());
        return user;
    }

    /**
     * Convert User (Entity) to UserDo
     *
     * @param user the source object
     * @return UserDo
     */
    public static UserDo e2do(User user) {
        UserDo userDo = new UserDo();
        userDo.setId(user.getId());
        userDo.setEmail(user.getEmail());
        userDo.setName(user.getName());
        String decoded = PasswdUtil.decode(user.getPassword());
        userDo.setPassword(decoded);
        userDo.setAvatar(user.getAvatar());
        userDo.setGender(user.getGender());
        userDo.setQq(user.getQq());
        userDo.setMobile(user.getMobile());
        userDo.setSubject(user.getSubject());
        userDo.setGrade(user.getGrade());
        return userDo;
    }

    /**
     * Convert UserDto to UserDo
     *
     * @param userDto the source object
     * @return UserDo
     */
    public static UserDo dto2do(UserDto userDto) {
        UserDo userDo = new UserDo();
        userDo.setEmail(userDto.getEmail());
        userDo.setName(userDto.getName());
        userDo.setAvatar(userDto.getAvatar());
        userDo.setGender(userDto.getGender());
        userDo.setQq(userDto.getQq());
        userDo.setMobile(userDto.getMobile());
        userDo.setSubject(userDto.getSubject());
        userDo.setGrade(userDto.getGrade());
        return userDo;
    }

    /**
     * Convert UserDo to User (Entity)
     *
     * @param req the source object
     * @return UserDo
     *
     */
    public static UserDo req2do(CreateUserReq req) {
        UserDo userDo = new UserDo();
        userDo.setEmail(req.getEmail());
        userDo.setName(req.getName());
        userDo.setPassword(req.getPassword());
        userDo.setGender(req.getGender());
        userDo.setQq(req.getQq());
        userDo.setMobile(req.getMobile());
        return userDo;
    }

    public static UserDo req2do(UpdateUserReq req) {
        UserDo userDo = new UserDo();
        userDo.setEmail(req.getEmail());
        userDo.setName(req.getName());
        userDo.setGender(req.getGender());
        userDo.setQq(req.getQq());
        userDo.setMobile(req.getMobile());
        userDo.setSubject(req.getSubject());
        userDo.setGrade(req.getGrade());
        return userDo;
    }

    public static UserServiceImpl.QueryParam req2param(LoginReq req) {
        UserServiceImpl.QueryParam param = new UserServiceImpl.QueryParam();
        param.setEmail(req.getEmail());
        param.setName(req.getName());
        return param;
    }
}