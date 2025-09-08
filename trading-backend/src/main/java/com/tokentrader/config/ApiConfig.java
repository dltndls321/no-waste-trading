package com.tokentrader.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api")
@Data
public class ApiConfig {
    
    private Kis kis = new Kis();
    private Claude claude = new Claude();
    private AiService aiService = new AiService();
    
    @Data
    public static class Kis {
        private String appKey;
        private String secretKey;
        private String baseUrl;
        private String accountNumber;
        private String accountProductCode;
    }
    
    @Data
    public static class Claude {
        private String apiKey;
        private String baseUrl;
        private String model;
        private Integer maxTokens;
        private Double temperature;
    }
    
    @Data
    public static class AiService {
        private String baseUrl;
        private Integer timeout;
    }
}