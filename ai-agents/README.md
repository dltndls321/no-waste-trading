# AI Agents

Python 기반 Claude AI 에이전트 마이크로서비스

## 📁 프로젝트 구조
```
ai-agents/
├── agents/                 # AI 에이전트 구현
│   ├── research_agent.py   # 종목 발굴 에이전트
│   ├── analysis_agent.py   # 기술적/재무적 분석 에이전트
│   ├── portfolio_agent.py  # 포트폴리오 최적화 에이전트
│   ├── risk_agent.py       # 리스크 관리 에이전트
│   ├── execution_agent.py  # 거래 실행 에이전트
│   └── coordinator_agent.py # 조율 에이전트
├── core/                   # 핵심 기능
│   ├── agent_base.py       # 에이전트 베이스 클래스
│   ├── claude_client.py    # Claude API 클라이언트
│   └── message_bus.py      # 에이전트 간 통신
├── apis/
│   └── fast_api.py         # REST API 서버
├── config/
│   └── settings.py         # 환경 설정
├── requirements.txt        # Python 의존성
└── Dockerfile             # 도커 이미지 설정
```

## 🛠 기술 스택
- Python 3.10+
- FastAPI
- Anthropic Claude API
- LangChain
- Redis (메시지 큐)

## 📌 에이전트 역할
1. **Research Agent**: 종목 발굴 및 시장 분석
2. **Analysis Agent**: 기술적/재무적 심층 분석
3. **Portfolio Agent**: 포트폴리오 최적화
4. **Risk Agent**: 리스크 평가 및 관리
5. **Execution Agent**: 거래 실행 전략
6. **Coordinator Agent**: 전체 조율 및 의사결정

## 🚀 시작하기
```bash
# 가상환경 생성
python -m venv venv
source venv/bin/activate  # Windows: venv\Scripts\activate

# 의존성 설치
pip install -r requirements.txt

# 서버 실행
uvicorn apis.fast_api:app --reload --port 8001
```