package com.lp.lpsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lp.lpsystem.user.dto.TeachDesignReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootTest
public class PyTest {
    @Test
    public void test() throws IOException, InterruptedException {
        String pythonScriptPath = "D:\\code1\\beike\\润\\教学计划第一步生成文本.py"; // 替换为你的Python脚本路径

        TeachDesignReq req = new TeachDesignReq();
        req.setGrade("1");
        req.setSubject("1");
        req.setTheme("1");
        req.setStudent_level("1");
        req.setClass_duration("1");
        req.setTeaching_style("1");

        ObjectMapper mapper = new ObjectMapper();
        String javaData = mapper.writeValueAsString(req);


        String[] command = {"python", pythonScriptPath, javaData};
        ProcessBuilder pb = new ProcessBuilder(command);
        Process process = pb.start();

        // 读取标准输出
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        // 读取标准错误输出（可选）
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        while ((line = errorReader.readLine()) != null) {
            System.err.println(line);
        }

        // 关闭读取器
        reader.close();
        errorReader.close();

        // 等待进程结束并获取退出值
        int exitCode = process.waitFor();
        System.out.println("\nExited with error code : " + exitCode);
    }

//    @Value("${teachdesign.filePath}")
//    private String filePath;

    @Test
    public void test2() throws IOException, InterruptedException {
        String filePath = "D:\\Java_code\\LPSystem\\src\\main\\resources\\json\\teachDesign.json";
        TeachDesignReq req = new TeachDesignReq();
        req.setGrade("1");
        req.setSubject("1");
        req.setTheme("1");
        req.setStudent_level("1");
        req.setClass_duration("1");
        req.setTeaching_style("1");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(req);

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
