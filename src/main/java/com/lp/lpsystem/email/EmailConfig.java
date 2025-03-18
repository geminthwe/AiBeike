package com.lp.lpsystem.email;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class EmailConfig {
    @Value("${email.config.host}")
    private String host;
    @Value("${email.config.port}")
    private int port;
    @Value("${email.config.sender}")
    private String sender;
    @Value("${email.config.password}")
    private String password;
    @Value("${email.config.protocol}")
    private String protocol;

}
