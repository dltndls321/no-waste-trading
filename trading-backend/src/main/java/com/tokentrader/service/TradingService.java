package com.tokentrader.service;

import com.tokentrader.api.claude.ClaudeApiService;
import com.tokentrader.api.kis.KisApiService;
import com.tokentrader.dto.AnalysisRequest;
import com.tokentrader.dto.AnalysisResponse;
import com.tokentrader.dto.TradingDecision;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradingService {

    private final KisApiService kisApiService;
    private final ClaudeApiService claudeApiService;
    private final AgentOrchestrationService agentService;

    public AnalysisResponse analyzeStock(AnalysisRequest request) {
        log.info("Starting analysis for symbol: {}", request.getSymbol());

        // 1. KIS API에서 시장 데이터 가져오기
        Map<String, Object> marketData = kisApiService.getMarketData(request.getSymbol());

        // 2. AI 에이전트들에게 분석 요청
        String researchResult = agentService.callResearchAgent(marketData);
        String analysisResult = agentService.callAnalysisAgent(marketData, researchResult);
        String riskAssessment = agentService.callRiskAgent(marketData, analysisResult);

        // 3. Coordinator Agent가 최종 의견 조율
        String finalAnalysis = agentService.callCoordinatorAgent(
                researchResult, analysisResult, riskAssessment
        );

        return AnalysisResponse.builder()
                .symbol(request.getSymbol())
                .research(researchResult)
                .analysis(analysisResult)
                .risk(riskAssessment)
                .recommendation(finalAnalysis)
                .build();
    }

    public TradingDecision getTradingDecision(String symbol) {
        log.info("Getting trading decision for: {}", symbol);

        // 분석 수행
        AnalysisRequest request = new AnalysisRequest();
        request.setSymbol(symbol);
        AnalysisResponse analysis = analyzeStock(request);

        // Execution Agent를 통한 거래 결정
        return agentService.callExecutionAgent(analysis);
    }

    public String executeTrade(TradingDecision decision) {
        log.info("Executing trade decision: {}", decision);

        // 리스크 체크
        if (!checkRiskConstraints(decision)) {
            return "Trade rejected: Risk constraints violated";
        }

        // KIS API를 통한 실제 거래 실행
        try {
            String orderId = kisApiService.placeOrder(
                    decision.getSymbol(),
                    decision.getAction(),
                    decision.getQuantity(),
                    decision.getPrice()
            );

            log.info("Order placed successfully. Order ID: {}", orderId);
            return "Order executed: " + orderId;

        } catch (Exception e) {
            log.error("Failed to execute trade", e);
            return "Trade execution failed: " + e.getMessage();
        }
    }

    public Map<String, Object> getPortfolioStatus() {
        Map<String, Object> status = new HashMap<>();

        // KIS API에서 포트폴리오 정보 가져오기
        Map<String, Object> accountInfo = kisApiService.getAccountInfo();
        Map<String, Object> positions = kisApiService.getPositions();

        // Portfolio Agent를 통한 분석
        String portfolioAnalysis = agentService.callPortfolioAgent(accountInfo, positions);

        status.put("account", accountInfo);
        status.put("positions", positions);
        status.put("analysis", portfolioAnalysis);

        return status;
    }

    private boolean checkRiskConstraints(TradingDecision decision) {
        // 리스크 제약 조건 체크
        // TODO: 구체적인 리스크 체크 로직 구현
        return true;
    }
}