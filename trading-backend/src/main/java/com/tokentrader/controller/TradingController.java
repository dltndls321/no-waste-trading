package com.tokentrader.controller;

import com.tokentrader.dto.AnalysisRequest;
import com.tokentrader.dto.AnalysisResponse;
import com.tokentrader.dto.TradingDecision;
import com.tokentrader.service.TradingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/trading")
@RequiredArgsConstructor
@Tag(name = "Trading", description = "Trading operations API")
public class TradingController {

    private final TradingService tradingService;

    @PostMapping("/analyze")
    @Operation(summary = "Analyze stock", description = "Analyze a stock using AI agents")
    public ResponseEntity<AnalysisResponse> analyzeStock(
            @Valid @RequestBody AnalysisRequest request) {
        log.info("Analyzing stock: {}", request.getSymbol());
        AnalysisResponse response = tradingService.analyzeStock(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/decision")
    @Operation(summary = "Get trading decision", description = "Get AI trading decision for a stock")
    public ResponseEntity<TradingDecision> getTradingDecision(
            @RequestParam String symbol) {
        log.info("Getting trading decision for: {}", symbol);
        TradingDecision decision = tradingService.getTradingDecision(symbol);
        return ResponseEntity.ok(decision);
    }

    @PostMapping("/execute")
    @Operation(summary = "Execute trade", description = "Execute a trading decision")
    public ResponseEntity<String> executeTrade(
            @Valid @RequestBody TradingDecision decision) {
        log.info("Executing trade: {}", decision);
        String result = tradingService.executeTrade(decision);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/portfolio")
    @Operation(summary = "Get portfolio", description = "Get current portfolio status")
    public ResponseEntity<?> getPortfolio() {
        log.info("Getting portfolio status");
        return ResponseEntity.ok(tradingService.getPortfolioStatus());
    }
}