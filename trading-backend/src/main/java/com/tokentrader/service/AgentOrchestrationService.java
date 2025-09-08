package com.tokentrader.service;

import com.tokentrader.api.claude.ClaudeApiService;
import com.tokentrader.dto.AnalysisResponse;
import com.tokentrader.dto.TradingDecision;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgentOrchestrationService {

    private final ClaudeApiService claudeApiService;

    public String callResearchAgent(Map<String, Object> marketData) {
        log.info("Calling Research Agent");

        String prompt = buildResearchPrompt(marketData);
        String response = claudeApiService.analyzeMarketData(marketData, "research");

        log.debug("Research Agent response: {}", response);
        return response;
    }

    public String callAnalysisAgent(Map<String, Object> marketData, String researchResult) {
        log.info("Calling Analysis Agent");

        Map<String, Object> analysisData = new HashMap<>(marketData);
        analysisData.put("research", researchResult);

        String response = claudeApiService.analyzeMarketData(analysisData, "analysis");

        log.debug("Analysis Agent response: {}", response);
        return response;
    }

    public String callRiskAgent(Map<String, Object> marketData, String analysisResult) {
        log.info("Calling Risk Agent");

        Map<String, Object> riskData = new HashMap<>(marketData);
        riskData.put("analysis", analysisResult);

        String response = claudeApiService.analyzeMarketData(riskData, "risk");

        log.debug("Risk Agent response: {}", response);
        return response;
    }

    public String callPortfolioAgent(Map<String, Object> accountInfo, Map<String, Object> positions) {
        log.info("Calling Portfolio Agent");

        Map<String, Object> portfolioData = new HashMap<>();
        portfolioData.put("account", accountInfo);
        portfolioData.put("positions", positions);

        String response = claudeApiService.analyzeMarketData(portfolioData, "portfolio");

        log.debug("Portfolio Agent response: {}", response);
        return response;
    }

    public TradingDecision callExecutionAgent(AnalysisResponse analysis) {
        log.info("Calling Execution Agent");

        Map<String, Object> executionData = new HashMap<>();
        executionData.put("symbol", analysis.getSymbol());
        executionData.put("analysis", analysis.getAnalysis());
        executionData.put("risk", analysis.getRisk());
        executionData.put("recommendation", analysis.getRecommendation());

        String response = claudeApiService.analyzeMarketData(executionData, "execution");

        // Parse execution response and create TradingDecision
        return parseTradingDecision(response, analysis.getSymbol());
    }

    public String callCoordinatorAgent(String research, String analysis, String risk) {
        log.info("Calling Coordinator Agent");

        Map<String, Object> coordinationData = new HashMap<>();
        coordinationData.put("research", research);
        coordinationData.put("analysis", analysis);
        coordinationData.put("risk", risk);

        String response = claudeApiService.analyzeMarketData(coordinationData, "coordinator");

        log.debug("Coordinator Agent response: {}", response);
        return response;
    }

    private String buildResearchPrompt(Map<String, Object> marketData) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("As a Research Agent, analyze the following market data:\n\n");

        // Format market data for the prompt
        if (marketData.containsKey("price")) {
            prompt.append("Current Price: ").append(marketData.get("price")).append("\n");
        }
        if (marketData.containsKey("volume")) {
            prompt.append("Volume: ").append(marketData.get("volume")).append("\n");
        }
        if (marketData.containsKey("change")) {
            prompt.append("Change: ").append(marketData.get("change")).append("%\n");
        }

        prompt.append("\nProvide investment insights including:\n");
        prompt.append("1. Market trend analysis\n");
        prompt.append("2. Key support and resistance levels\n");
        prompt.append("3. Volume analysis\n");
        prompt.append("4. Sentiment analysis\n");
        prompt.append("5. Recommendation\n");

        return prompt.toString();
    }

    private TradingDecision parseTradingDecision(String executionResponse, String symbol) {
        // Simple parsing logic - in production, use proper JSON parsing
        String action = "HOLD"; // Default
        Integer quantity = 0;
        Double price = null;

        if (executionResponse.toLowerCase().contains("buy")) {
            action = "BUY";
            quantity = 10; // Default quantity
        } else if (executionResponse.toLowerCase().contains("sell")) {
            action = "SELL";
            quantity = 10;
        }

        return TradingDecision.builder()
                .symbol(symbol)
                .action(action)
                .quantity(quantity)
                .price(price)
                .orderType("MARKET")
                .reason(executionResponse)
                .validUntil(LocalDateTime.now().plusHours(1))
                .agentConsensus(executionResponse)
                .build();
    }
}