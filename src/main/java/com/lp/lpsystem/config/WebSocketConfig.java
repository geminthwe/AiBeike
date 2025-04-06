package com.lp.lpsystem.config;

import com.lp.lpsystem.handler.AiWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final AiWebSocketHandler aiWebSocketHandler;

    // 通过构造函数注入AiWebSocketHandler
    public WebSocketConfig(AiWebSocketHandler aiWebSocketHandler) {
        this.aiWebSocketHandler = aiWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 使用注入的aiWebSocketHandler实例
        registry.addHandler(aiWebSocketHandler, "/api/ai/websocket").setAllowedOrigins("*");
    }
}