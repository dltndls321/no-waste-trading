# Claude Agents

Claude Code CLI를 활용한 AI 분석 자동화 스크립트

## 📋 개요

Claude Max 플랜을 활용하여 API 비용 없이 고급 AI 분석을 수행하는 자동화 시스템입니다.

## 📁 디렉토리 구조

```
claude-agents/
├── daily/              # 일일 실행 스크립트
│   ├── morning-analysis.bat       # 06:00 - 시장 분석
│   ├── signal-generation.bat      # 06:30 - 매매 신호
│   └── closing-analysis.bat       # 22:30 - 종가 분석
├── weekly/             # 주간 실행 스크립트
│   └── portfolio-rebalance.bat    # 일요일 - 리밸런싱
├── prompts/            # 프롬프트 템플릿
│   ├── research.txt    # Research Agent 프롬프트
│   ├── analysis.txt    # Analysis Agent 프롬프트
│   └── portfolio.txt   # Portfolio Agent 프롬프트
└── utils/              # 유틸리티 스크립트
    └── json-validator.py           # JSON 검증
```

## 🤖 에이전트별 역할

### 1. Research Agent (일 1회 - 06:00)
```batch
claude "오늘 미국 시장 분석..." > ../data/analysis/daily_research.json
```
- 시장 동향 분석
- 주요 뉴스 정리
- 섹터별 트렌드

### 2. Analysis Agent (일 2회 - 06:30, 22:30)
```batch
claude "기술적 분석..." > ../data/signals/trading_signals.json
```
- 기술적 지표 분석
- 매매 신호 생성
- 진입/청산 가격 제시

### 3. Portfolio Agent (주 1회 - 일요일)
```batch
claude "포트폴리오 리밸런싱..." > ../data/analysis/weekly_rebalance.json
```
- 포트폴리오 성과 분석
- 리밸런싱 제안
- 자산 배분 최적화

## 📅 실행 스케줄

| 시간 | 스크립트 | 출력 파일 |
|------|---------|-----------|
| 06:00 | morning-analysis.bat | daily_research_{date}.json |
| 06:30 | signal-generation.bat | trading_signals_{date}.json |
| 22:30 | closing-analysis.bat | closing_analysis_{date}.json |
| 23:00 | daily-report.bat | daily_report_{date}.json |
| 일요일 22:00 | portfolio-rebalance.bat | weekly_rebalance_{date}.json |

## 🚀 사용 방법

### 수동 실행
```batch
cd claude-agents/daily
./morning-analysis.bat
```

### 자동화 설정 (Windows Task Scheduler)
```batch
cd ../scheduler
./setup-windows-scheduler.bat
```

### 프롬프트 커스터마이징
`prompts/` 디렉토리의 템플릿 파일을 수정하여 분석 방식을 조정할 수 있습니다.

## 📊 출력 형식

### trading_signals.json 예시
```json
{
  "timestamp": "2024-01-15T06:30:00Z",
  "signals": [
    {
      "symbol": "AAPL",
      "action": "BUY",
      "confidence": 0.85,
      "entry_price": 185.50,
      "stop_loss": 183.00,
      "take_profit": 190.00,
      "rationale": "강한 상승 모멘텀"
    }
  ]
}
```

## 🔧 환경 요구사항

- Claude Code CLI 설치 및 로그인
- Windows 10/11 (Task Scheduler용)
- Python 3.10+ (JSON 검증용 - 선택사항)

## 📝 프롬프트 작성 가이드

1. **구조화된 출력 요청**: JSON 형식 명시
2. **구체적인 지표**: 사용할 기술적 지표 명시
3. **리스크 관리**: 손절/익절 가격 포함
4. **신뢰도 점수**: 각 신호의 확신도 포함

## 🐛 트러블슈팅

### Claude Code CLI 인증 문제
```batch
claude login
```

### JSON 파싱 오류
```batch
python utils/json-validator.py ../data/signals/trading_signals.json
```

### 스케줄러 실행 실패
- Windows Task Scheduler에서 작업 기록 확인
- 배치 파일 경로가 절대 경로인지 확인

## 💰 비용 절감 효과

- Claude API: ~$0.01/1K tokens
- Claude Code CLI: $0 (Max 플랜 포함)
- **월 예상 절감액**: $50-100+

## 📌 주의사항

- Claude Code는 세션당 컨텍스트가 제한적
- 복잡한 대화형 작업은 어려움
- 출력 형식 일관성 유지 중요