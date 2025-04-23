package com.lp.lpsystem.config;


import com.lp.lpsystem.auth.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        registry.addInterceptor(authInterceptor).addPathPatterns("/**")
                .excludePathPatterns(
                        "/hello",
                        "/user/login",
                        "/user/validCode/**",
                        "/user/register",
                        // Swagger UI v2 (if applicable)
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/v2/api-docs",
                        "/webjars/**",
                        // Swagger UI v3
                        "/v3/api-docs/**",
                        "/swagger-ui/**"
                );
    }

}