package com.lp.lpsystem.call.controller;


import com.lp.lpsystem.call.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class aiController {
    private AiService aiService;

    public aiController(AiService aiService) {
        this.aiService = aiService;
    }

    @Autowired
    public void AiController(AiService aiService) {
        this.aiService = aiService;
    }

    /**
     * 获取最新的教学设计 ID
     */
    @GetMapping("/latest-plan-id")
    public ResponseEntity<Map<String, Object>> getLatestPlanId() {
        try {
            String latestPlanId = aiService.getLatestPlanId();
            return ResponseEntity.ok(Map.of("latest_plan_id", latestPlanId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to retrieve latest plan ID: " + e.getMessage()));
        }
    }
}
