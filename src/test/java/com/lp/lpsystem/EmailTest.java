package com.lp.lpsystem;

import com.lp.lpsystem.email.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public class EmailTest {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailService emailService;

    @Test
    public void testSendVerificationCode(){
        //String toEmail = "2139651898@qq.com"; // 替换为目标测试邮箱地址
        String toEmail = "2761027922@qq.com"; // 替换为目标测试邮箱地址
        String generatedCode = "123456"; // 这里可以使用实际生成验证码的方法

        // 调用sendVerificationCode方法
        //emailService.sendVerificationCode(toEmail, generatedCode);
    }
}
