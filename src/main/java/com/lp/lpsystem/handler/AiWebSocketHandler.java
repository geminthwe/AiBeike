package com.lp.lpsystem.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lp.lpsystem.call.dto.LessonPlan;
import com.lp.lpsystem.call.service.AiService;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AiWebSocketHandler extends TextWebSocketHandler {

    private final AiService aiService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public AiWebSocketHandler(AiService aiService) {
        this.aiService = aiService;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            String payload = message.getPayload();
            Map<String, Object> data = objectMapper.readValue(payload, Map.class);

            if ("TeachingPlan".equals(data.get("type"))) {
                // 生成教学计划
                String response = aiService.teachPlan(data);
                System.out.println("response: " + response);

                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(response)));
            } else if("SubjectAnalysis".equals(data.get("type"))) {
                // 学情分析
                String pythonResponse = aiService.subjectAnalysis(data.get("params"));
                System.out.println("pythonResponse: " + pythonResponse);
                // 渲染 Markdown 为 HTML
                String renderedHtml = renderMarkdownToHtml(pythonResponse);
                System.out.println("renderedHtml: " + renderedHtml);

                // 构造响应数据
                Map<String, String> response = new HashMap<>();
                response.put("analysisHtml", renderedHtml);

                // 发送渲染后的 HTML 给前端
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(response)));
            }else if("QuestionGenerator".equals(data.get("type"))) {
                String content = aiService.questionGenerator(data);
                System.out.println("content: " + content);

                // 渲染 Markdown 为 HTML
                String renderedHtml = renderMarkdownToHtml(content);
                System.out.println("renderedHtml: " + renderedHtml);

                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(content)));
            }else if("history".equals(data.get("action"))) {

            }
        } catch (Exception e) {
            e.printStackTrace();
            session.close(CloseStatus.SERVER_ERROR.withReason("Error handling message"));
        }
    }

    private String renderMarkdownToHtml(String markdown) {
        // 使用 commonmark-java 渲染 Markdown
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    private String convertHistoryToHtml(List<Map<String, Object>> history) {
        StringBuilder htmlBuilder = new StringBuilder();

        for (Map<String, Object> item : history) {
            String role = (String) item.get("role");
            String content = (String) item.get("content");

            // 渲染 Markdown 为 HTML
            String renderedContent;
            if ("assistant".equals(role)) {
                renderedContent = renderMarkdownToHtml(content); // 助手消息渲染 Markdown
            } else {
                renderedContent = escapeHtml(content); // 用户消息仅转义 HTML
            }

            if ("user".equals(role)) {
                // 用户消息的 HTML 样式
                htmlBuilder.append("<div class='message user-message'><pre>")
                        .append(renderedContent)
                        .append("</pre></div>");
            } else if ("assistant".equals(role)) {
                // 助手消息的 HTML 样式
                htmlBuilder.append("<div class='message assistant-message'><pre>")
                        .append(renderedContent)
                        .append("</pre></div>");
            }
        }

        return htmlBuilder.toString();
    }

    /**
     * 转义 HTML 特殊字符，防止 XSS 攻击。
     */
    private String escapeHtml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
