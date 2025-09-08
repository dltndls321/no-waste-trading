package com.tokentrader.api.claude;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokentrader.config.ApiConfig;
import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClaudeApiService {
    
    private final ApiConfig apiConfig;
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final Bucket claudeApiBucket;
    
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    
    public String sendMessage(String prompt) {
        return sendMessage(prompt, apiConfig.getClaude().getMaxTokens());
    }
    
    public String sendMessage(String prompt, int maxTokens) {
        // Rate limiting
        if (!claudeApiBucket.tryConsume(1)) {
            log.warn("Claude API rate limit reached, waiting...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        String url = apiConfig.getClaude().getBaseUrl() + "/messages";
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", apiConfig.getClaude().getModel());
        requestBody.put("max_tokens", maxTokens);
        requestBody.put("temperature", apiConfig.getClaude().getTemperature());
        
        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);
        messages.add(message);
        requestBody.put("messages", messages);
        
        try {
            String json = objectMapper.writeValueAsString(requestBody);
            RequestBody body = RequestBody.create(json, JSON);
            
            Request request = new Request.Builder()
                    .url(url)
                    .header("x-api-key", apiConfig.getClaude().getApiKey())
                    .header("anthropic-version", "2023-06-01")
                    .header("content-type", "application/json")
                    .post(body)
                    .build();
            
            try (Response response = httpClient.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    log.error("Claude API error: {}", response.body().string());
                    throw new IOException("Unexpected response: " + response);
                }
                
                String responseBody = response.body().string();
                Map<String, Object> result = objectMapper.readValue(responseBody, Map.class);
                
                List<Map<String, Object>> content = (List<Map<String, Object>>) result.get("content");
                if (content != null && !content.isEmpty()) {
                    return (String) content.get(0).get("text");
                }
                
                return "";
            }
        } catch (Exception e) {
            log.error("Failed to call Claude API", e);
            throw new RuntimeException("Failed to call Claude API", e);
        }
    }
    
    public String analyzeMarketData(Map<String, Object> marketData, String agentType) {
        String prompt = buildAgentPrompt(agentType, marketData);
        return sendMessage(prompt);
    }
    
    private String buildAgentPrompt(String agentType, Map<String, Object> data) {
        StringBuilder prompt = new StringBuilder();
        
        switch (agentType) {
            case "research":
                prompt.append("You are a Research Agent specializing in stock market analysis.\n");
                prompt.append("Analyze the following market data and provide investment insights:\n");
                break;
            case "analysis":
                prompt.append("You are an Analysis Agent specializing in technical and fundamental analysis.\n");
                prompt.append("Perform deep analysis on the following data:\n");
                break;
            case "risk":
                prompt.append("You are a Risk Management Agent.\n");
                prompt.append("Assess the risk factors in the following investment scenario:\n");
                break;
            case "portfolio":
                prompt.append("You are a Portfolio Management Agent.\n");
                prompt.append("Optimize the portfolio based on the following data:\n");
                break;
            case "execution":
                prompt.append("You are an Execution Agent.\n");
                prompt.append("Determine the best execution strategy for:\n");
                break;
            case "coordinator":
                prompt.append("You are a Coordinator Agent managing multiple AI agents.\n");
                prompt.append("Synthesize the following analyses and make a final decision:\n");
                break;
            default:
                prompt.append("Analyze the following data:\n");
        }
        
        prompt.append("\n").append(data.toString());
        prompt.append("\n\nProvide your analysis in a structured format.");
        
        return prompt.toString();
    }
}