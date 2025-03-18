package com.lp.lpsystem.user.context;

import com.lp.lpsystem.user.domain.UserDo;

public class UserInfoContext {

    private static final ThreadLocal<UserDo> userThreadLocal = new ThreadLocal<>();

    public static void clear() {
        userThreadLocal.remove();
    }

    public static UserDo getCurrentUser() {
        return userThreadLocal.get();
    }

    public static void setCurrentUser(UserDo user) {
        userThreadLocal.set(user);
    }

}
