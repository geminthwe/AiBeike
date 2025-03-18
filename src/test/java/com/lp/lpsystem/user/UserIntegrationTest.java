package com.lp.lpsystem.user;

import com.lp.lpsystem.LpSystemApplication;
import com.lp.lpsystem.common.utils.JwtUtil;
import com.lp.lpsystem.user.dto.*;
import com.lp.lpsystem.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(classes = {LpSystemApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserIntegrationTest {

    protected RestTemplate restTemplate = (new TestRestTemplate()).getRestTemplate();

    @Autowired
    private UserService userService;

    @Test
    void testRegister() {
        try {
            String token = JwtUtil.createTokenByUserName("gemin");

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", token);

            CreateUserReq req = new CreateUserReq();

            req.setName("gemin1");
            req.setPassword("gemin123456");
            req.setEmail("guoyumingabcd@qq.com");
            req.setGender("man");
            req.setMobile("13111111111");
            req.setQq("3337928693");
            HttpEntity<CreateUserReq> request = new HttpEntity<>(req, headers);

            ResponseEntity<String> resp = restTemplate.postForEntity("http://localhost:6096/user/register", request, String.class);
            System.out.println("resp: "+ resp);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testLogin() {
        try {
            String token = JwtUtil.createTokenByUserName("gemin");
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", token);
            LoginReq req = new LoginReq();
            req.setName("gemin");
            req.setPassword("gemin123456");
            HttpEntity<LoginReq> request = new HttpEntity<>(req, headers);
            ResponseEntity<String> resp = restTemplate.postForEntity("http://localhost:6096/user/login", request, String.class);
            System.out.println("resp: "+ resp);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testGetUserInfo() {
        try {
            String token = JwtUtil.createTokenByUserName("ge");
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", token);
            HttpEntity<String> request = new HttpEntity<>(headers);
            ResponseEntity<String> resp = restTemplate.exchange("http://localhost:6096/user/info", HttpMethod.GET,request, String.class);
            System.out.println("resp: "+ resp);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testUpdateUserInfo() {
        try {
            String token = JwtUtil.createTokenByUserName("gemin");
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", token);

            UpdateUserReq req = new UpdateUserReq();
            req.setName("gemin");
            req.setGender("man");
            req.setEmail("guoyumingabcd@qq.com");
            req.setMobile("13111111111");
            req.setQq("3337928693");
            req.setSubject("math");
            req.setGrade("1");

            HttpEntity<UpdateUserReq> request = new HttpEntity<>(req, headers);


            ResponseEntity<String> resp = restTemplate.exchange("http://localhost:6096/user", HttpMethod.PUT, request, String.class);
            System.out.println("resp: "+ resp);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testUpdatePassword() {
        try {
            String token = JwtUtil.createTokenByUserName("gemin");
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", token);
            UpdatePasswdReq req = new UpdatePasswdReq();
            req.setOldPassword("gemin123456");
            req.setNewPassword("gemin1234567");
            HttpEntity<UpdatePasswdReq> request = new HttpEntity<>(req, headers);
            ResponseEntity<String> resp = restTemplate.exchange("http://localhost:6096/user/password", HttpMethod.PUT, request, String.class);
            System.out.println("resp: "+ resp);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testSendValidCode() {
        try {
            String email = "3337928693@qq.com";
            HttpHeaders headers = new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<>(null,headers);

            ResponseEntity<String> resp = restTemplate.exchange("http://localhost:6096/user/validCode?email="+email, HttpMethod.GET, entity, String.class);
            System.out.println("resp: "+ resp);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testCheckValidCode() {
        try {
            String token = JwtUtil.createTokenByUserName("gemin");
            System.out.println(token);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", token);
            CheckCodeReq req = new CheckCodeReq();
            req.setEmail("guoyumingabcd@qq.com");
            req.setCode("4662");
            HttpEntity<CheckCodeReq> request = new HttpEntity<>(req, headers);
            ResponseEntity<String> resp = restTemplate.postForEntity("http://localhost:6096/user/validCode/check", request, String.class);
            System.out.println("resp: "+ resp);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
