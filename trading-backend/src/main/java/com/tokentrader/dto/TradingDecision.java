package com.tokentrader.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TradingDecision {

    @NotBlank
    private String symbol;

    @NotBlank
    private String action; // BUY, SELL, HOLD

    @NotNull
    @Min(1)
    private Integer quantity;

    private Double price; // null for market order

    private String orderType; // MARKET, LIMIT

    private String reason;

    private Double expectedReturn;

    private Double riskScore;

    private LocalDateTime validUntil;

    private String agentConsensus; // Summary of all agents' opinions
}