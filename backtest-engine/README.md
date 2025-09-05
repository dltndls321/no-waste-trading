# Backtest Engine

백테스팅 및 전략 검증 엔진

## 📁 프로젝트 구조
```
backtest-engine/
├── engine/
│   ├── backtester.py       # 백테스팅 핵심 엔진
│   ├── simulator.py        # 거래 시뮬레이터
│   └── evaluator.py        # 성과 평가
├── data/
│   ├── loader.py           # 데이터 로더
│   └── preprocessor.py     # 데이터 전처리
├── strategies/
│   └── ai_strategy.py      # AI 에이전트 전략
├── reports/
│   ├── generator.py        # 리포트 생성
│   └── visualizer.py       # 차트 및 시각화
├── tests/
│   └── test_strategies.py  # 전략 테스트
├── requirements.txt
└── Dockerfile
```

## 🛠 기술 스택
- Python 3.10+
- pandas, numpy
- matplotlib, plotly
- yfinance (데이터 수집)
- PyPortfolioOpt (포트폴리오 최적화)

## 📊 주요 지표
- 연환산 수익률 (CAGR)
- 샤프 비율 (Sharpe Ratio)
- 최대 낙폭 (MDD)
- 승률 및 손익비
- 알파/베타

## 🚀 사용법
```bash
# 백테스팅 실행
python engine/backtester.py --strategy ai_strategy --period 2020-2023

# 리포트 생성
python reports/generator.py --result backtest_result.json
```