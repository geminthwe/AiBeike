package com.lp.lpsystem.auth.interceptor;

import com.lp.lpsystem.common.exception.http.BadRequestException;
import com.lp.lpsystem.common.utils.JwtUtil;
import com.lp.lpsystem.user.context.UserInfoContext;
import com.lp.lpsystem.user.domain.UserDo;
import com.lp.lpsystem.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取用户信息
        String header = request.getHeader("Authorization");
        if (header == null) {
            throw new BadRequestException("未登录");
        }

        if (!JwtUtil.verify(header)) {
            throw new BadRequestException("token验证失败 请重新登录");
        }

        String userName = JwtUtil.getUserId(header);
        if (userName == null) {
            throw new BadRequestException("token验证失败 请重新登录");
        }
//         String requestURI = request.getRequestURI();
//         System.out.println("Request URI: " + requestURI + ", User Name: " + userName);

        UserDo userDo = userService.getUserByName(userName);

        if (userDo == null) {
            throw new BadRequestException("用户不存在");
        }

        UserInfoContext.setCurrentUser(userDo);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        UserInfoContext.clear();
    }
}
