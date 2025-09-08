package com.tokentrader.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AnalysisResponse {

    private String symbol;

    private String research;

    private String analysis;

    private String risk;

    private String recommendation;

    private Double targetPrice;

    private Double stopLoss;

    private String sentiment; // BULLISH, BEARISH, NEUTRAL

    private Integer confidenceScore; // 0-100

    private LocalDateTime analyzedAt;
}