package com.tokentrader.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AnalysisRequest {
    
    @NotBlank(message = "Symbol is required")
    private String symbol;
    
    private String analysisType = "full"; // full, quick, technical, fundamental
    
    private Integer lookbackDays = 30;
    
    private Boolean includeNews = true;
    
    private Boolean includeRiskAssessment = true;
}