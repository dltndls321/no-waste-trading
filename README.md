# No Waste Trading 🤖💰

> Claude Pro 토큰을 활용한 AI 멀티 에이전트 자동매매 시스템

## 🎯 프로젝트 소개

비싼 Claude Pro 구독의 남는 토큰이 아까우신가요?  
주식 지식이 없어도 AI가 대신 분석하고 거래해드립니다!

### 핵심 특징
- 💡 **토큰 활용**: Claude Pro 플랜의 남는 토큰을 효율적으로 활용
- 🤖 **멀티 에이전트**: 6개의 전문 AI 에이전트가 협업하여 투자 결정
- 📊 **자동 분석**: 시장 분석부터 거래 실행까지 완전 자동화
- 🛡️ **리스크 관리**: 전담 리스크 에이전트가 안전장치 역할

## 🏗️ 시스템 아키텍처

```
┌─────────────────────────────────────────────┐
│            AI Agent Layer (Python)           │
│  Research | Analysis | Portfolio | Risk     │
│  Execution | Coordinator                    │
├─────────────────────────────────────────────┤
│         Trading Backend (Java/Spring)        │
│  API Gateway | Business Logic | Database    │
├─────────────────────────────────────────────┤
│            External APIs                     │
│  KIS API | Claude API | Market Data         │
└─────────────────────────────────────────────┘
```

## 📁 프로젝트 구조

```
no-waste-trading/
├── trading-backend/     # Java Spring Boot 메인 서버
├── ai-agents/          # Python AI 에이전트 서비스
├── backtest-engine/    # 백테스팅 엔진
├── frontend/           # 웹 UI (React)
├── docs/               # 프로젝트 문서
├── config/             # Docker, K8s 설정
├── scripts/            # 자동화 스크립트
├── data/               # 데이터 저장소
└── monitoring/         # 모니터링 설정
```

## 🛠 기술 스택

### Backend
- **Java 17** / Spring Boot 3.x
- **PostgreSQL** / Redis
- **한국투자증권 KIS API**

### AI/ML
- **Python 3.10+**
- **Claude API** (Anthropic)
- **LangChain** / FastAPI

### Infrastructure
- **Docker** / Docker Compose
- **GitHub Actions** (CI/CD)
- **Grafana** / Prometheus (모니터링)

## 🤖 AI 에이전트 소개

| 에이전트 | 역할 | 주요 기능 |
|---------|------|-----------|
| Research Agent | 종목 발굴 | 시장 트렌드 분석, 종목 스크리닝 |
| Analysis Agent | 심층 분석 | 기술적/재무적 분석, 밸류에이션 |
| Portfolio Agent | 포트폴리오 관리 | 자산 배분, 리밸런싱 |
| Risk Agent | 리스크 관리 | 위험 평가, 손절선 설정 |
| Execution Agent | 거래 실행 | 주문 타이밍, 슬리피지 최소화 |
| Coordinator Agent | 전체 조율 | 에이전트 간 의견 조율, 최종 결정 |

## 🚀 시작하기

### 사전 요구사항
- Java 17+
- Python 3.10+
- Docker & Docker Compose
- Claude API Key
- 한국투자증권 API Key

### 설치 및 실행

1. **환경 변수 설정**
```bash
cp .env.example .env
# .env 파일에 API 키 설정
```

2. **Docker Compose로 실행**
```bash
docker-compose up -d
```

3. **웹 UI 접속**
```
http://localhost:3000
```

## 📈 개발 로드맵

- [x] 프로젝트 구조 설계
- [ ] Spring Boot 백엔드 구축
- [ ] AI 에이전트 구현
- [ ] 백테스팅 엔진 개발
- [ ] 모의투자 테스트
- [ ] 웹 UI 개발
- [ ] 실거래 연동

## 📊 성과 목표

- 샤프 비율 > 1.0
- 연환산 수익률 > 15%
- 최대 낙폭 < 15%
- 월 평균 거래 횟수: 20-30회

## 🤝 기여하기

이슈와 PR은 언제나 환영합니다!

## 📄 라이선스

MIT License

## 👨‍💻 개발자

- GitHub: [@your-github](https://github.com/your-github)
- Email: your-email@example.com

---

**⚠️ 투자 주의사항**: 이 시스템은 교육 및 연구 목적으로 개발되었습니다. 실제 투자에 사용할 경우 손실 위험이 있으므로 신중하게 판단하시기 바랍니다.