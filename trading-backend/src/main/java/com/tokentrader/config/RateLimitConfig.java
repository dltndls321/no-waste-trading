package com.tokentrader.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimitConfig {

    @Bean
    public Bucket kisApiBucket() {
        // 초당 15회, 버스트 20회
        Bandwidth limit = Bandwidth.classic(20,
                Refill.intervally(15, Duration.ofSeconds(1)));
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @Bean
    public Bucket claudeApiBucket() {
        // 분당 60회
        Bandwidth limit = Bandwidth.classic(60,
                Refill.intervally(60, Duration.ofMinutes(1)));
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}