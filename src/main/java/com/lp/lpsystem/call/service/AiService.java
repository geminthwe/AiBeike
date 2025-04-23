package com.lp.lpsystem.call.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AiService {

    private final RestTemplate restTemplate;
    private final String pythonServiceUrl;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public AiService(RestTemplate restTemplate, @Value("${python.service.url}") String pythonServiceUrl) {
        this.restTemplate = restTemplate;
        this.pythonServiceUrl = pythonServiceUrl;
    }

    /**
     * 生成教学计划
     * @param params
     * @return
     * @throws JsonProcessingException
     */
    public String teachPlan(Object params) throws JsonProcessingException {
        // 构造请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 构造请求体
        Map<String, Object> requestBody = Map.of("params", params);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        // 发送 POST 请求到 Python 服务
        ResponseEntity<String> response = restTemplate.postForEntity(pythonServiceUrl + "/api/services/TeachingPlan/request", request, String.class);

        // 解析响应
        String responseBody = response.getBody();
        if (responseBody == null) {
            throw new RuntimeException("No response from Python service.");
        }

        Map<String, Object> responseData = objectMapper.readValue(responseBody, Map.class);
        System.out.println("Response data: " + responseData);
        return responseData.get("result").toString();
    }

    /**
     * 学生分析
     * @param params
     * @return
     * @throws Exception
     */
    public String subjectAnalysis(Object params) throws Exception {
        // 构造请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 构造请求体
        Map<String, Object> requestBody = Map.of("params", params);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        // 发送 POST 请求到 Python 服务
        ResponseEntity<String> response = restTemplate.postForEntity(pythonServiceUrl + "/api/services/SubjectAnalysis/request", request, String.class);

        // 解析响应
        String responseBody = response.getBody();
        if (responseBody == null) {
            throw new RuntimeException("No response from Python service.");
        }

        Map<String, Object> responseData = objectMapper.readValue(responseBody, Map.class);
        System.out.println("Response data: " + responseData);
        return responseData.get("result").toString();
    }


    /**
     * 生成题目
     * @param params
     * @return
     * @throws JsonProcessingException
     */
    public String questionGenerator(Object params) throws JsonProcessingException {
        // 构造请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 构造请求体
        Map<String, Object> requestBody = Map.of("params", params);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        // 发送 POST 请求到 Python 服务
        ResponseEntity<String> response = restTemplate.postForEntity(pythonServiceUrl + "/api/services/QuestionGenerator/request", request, String.class);

        // 解析响应
        String responseBody = response.getBody();
        if (responseBody == null) {
            throw new RuntimeException("No response from Python service.");
        }

        Map<String, Object> responseData = objectMapper.readValue(responseBody, Map.class);
        System.out.println("Response data: " + responseData);
        return responseData.get("result").toString();
    }

    public String getLatestPlanId() {
        ResponseEntity<Map> response = restTemplate.getForEntity(pythonServiceUrl + "/latest_plan_id", Map.class);
        Map<String, Object> body = response.getBody();

        if (body == null || !body.containsKey("latest_plan_id")) {
            throw new RuntimeException("Failed to retrieve latest plan ID from Python service.");
        }

        return (String) body.get("latest_plan_id");
    }

    public static String extractJson(String rawResponse) {
        // 定义正则表达式，匹配以 { 开头、以 } 结尾的 JSON 数据
        Pattern jsonPattern = Pattern.compile("\\{.*\\}", Pattern.DOTALL);
        Matcher matcher = jsonPattern.matcher(rawResponse);

        if (matcher.find()) {
            // 返回匹配到的 JSON 数据
            return matcher.group(0);
        } else {
            throw new IllegalArgumentException("No valid JSON data found in the response.");
        }
    }

    /**
     * 自动修复 JSON 数据中的非标准格式
     */
    private static String fixJson(String rawJson) {
        // 支持多种单位（分钟、秒、小时）
        return rawJson.replaceAll("(\\d+)(分钟|秒|小时)", "\"$1$2\"");
    }

    

}
