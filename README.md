# No Waste Trading 🤖💰

> Claude Max 토큰을 활용한 AI 멀티 에이전트 자동매매 시스템

## 🎯 프로젝트 소개

비싼 Claude Max 구독의 남는 토큰이 아까우신가요?  
주식 지식이 없어도 AI가 대신 분석하고 거래해드립니다!

### 핵심 특징
- 💡 **토큰 활용**: Claude Max 플랜의 남는 토큰을 효율적으로 활용
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
- Java 17+ (Spring Boot 3.x)
- Maven 3.6+
- Python 3.10+ (AI 에이전트용)
- Redis (선택사항)
- PostgreSQL (운영환경용)

### API 키 발급
1. **한국투자증권 KIS API**
   - [KIS Developers](https://apiportal.koreainvestment.com/) 가입
   - 모의투자 계좌 신청
   - 앱 등록 및 API 키 발급

2. **Claude API**
   - [Anthropic Console](https://console.anthropic.com/) 가입
   - API 키 발급

### 설치 및 실행

1. **프로젝트 클론**
```bash
git clone https://github.com/dltndls321/no-waste-trading.git
cd no-waste-trading
```

2. **환경 변수 설정**
```bash
cp .env.example .env
# .env 파일 편집하여 API 키 입력
```

3. **백엔드 서버 실행**
```bash
cd trading-backend

# Maven 설정 (nexus 저장소 우회)
mvn -s settings-override.xml clean compile

# 개발 모드 실행 (모의투자)
mvn -s settings-override.xml spring-boot:run

# 또는 IntelliJ IDEA에서 실행
# TradingBackendApplication.java 실행
```

4. **API 문서 확인**
```
http://localhost:8080/swagger-ui  # API 문서
http://localhost:8080/h2-console   # H2 DB 콘솔 (dev 모드)
http://localhost:8080/actuator/health  # 헬스체크
```

### 프로파일별 실행

```bash
# 개발 환경 (모의투자, H2 DB)
mvn -s settings-override.xml spring-boot:run -Dspring.profiles.active=dev

# 운영 환경 (실거래, PostgreSQL)
mvn -s settings-override.xml spring-boot:run -Dspring.profiles.active=prod

# 테스트 환경
mvn -s settings-override.xml spring-boot:run -Dspring.profiles.active=test
```

### 환경 변수 구조

```bash
# .env 파일 예시
# 모의투자용 (dev/test 프로파일)
KIS_MOCK_APP_KEY=your_mock_app_key
KIS_MOCK_SECRET_KEY=your_mock_secret_key
KIS_MOCK_ACCOUNT_NUMBER=12345678
KIS_MOCK_ACCOUNT_PRODUCT_CODE=01

# 실거래용 (prod 프로파일)
KIS_REAL_APP_KEY=your_real_app_key
KIS_REAL_SECRET_KEY=your_real_secret_key
KIS_REAL_ACCOUNT_NUMBER=87654321
KIS_REAL_ACCOUNT_PRODUCT_CODE=01

# Claude API (공통)
CLAUDE_API_KEY=your_claude_api_key

# 프로파일 설정
SPRING_PROFILES_ACTIVE=dev
```

## 📈 개발 로드맵

### Phase 1: 기반 구축 ✅
- [x] 프로젝트 구조 설계
- [x] Spring Boot 백엔드 구축
- [x] 환경별 설정 분리 (dev/prod/test)
- [x] KIS API / Claude API 서비스 구현
- [x] Rate Limiting 설정

### Phase 2: API 연동 🚧
- [ ] KIS API 키 발급 및 테스트
- [ ] Claude API 키 발급 및 테스트
- [ ] 시세 조회 API 연동
- [ ] 주문 API 연동

### Phase 3: AI 에이전트
- [ ] Python AI 에이전트 서비스 구축
- [ ] Research Agent 구현
- [ ] Analysis Agent 구현
- [ ] Portfolio Agent 구현
- [ ] Risk Agent 구현
- [ ] Execution Agent 구현
- [ ] Coordinator Agent 구현

### Phase 4: 백테스팅 & 검증
- [ ] 백테스팅 엔진 개발
- [ ] 과거 데이터 수집
- [ ] 전략 백테스팅
- [ ] 모의투자 테스트

### Phase 5: 프로덕션
- [ ] 웹 UI 개발
- [ ] 모니터링 시스템 구축
- [ ] 실거래 연동
- [ ] 운영 자동화

## 📊 성과 목표

- 샤프 비율 > 1.0
- 연환산 수익률 > 15%
- 최대 낙폭 < 15%
- 월 평균 거래 횟수: 20-30회

## 🔍 주요 기능

### 현재 구현된 기능
- ✅ Spring Boot 백엔드 서버
- ✅ KIS API 연동 서비스 (시세조회, 주문)
- ✅ Claude API 연동 서비스
- ✅ Rate Limiting (초당 15회)
- ✅ 환경별 설정 분리
- ✅ Swagger API 문서
- ✅ H2 인메모리 DB (개발용)

### 개발 예정 기능
- 🚧 AI 에이전트 시스템
- 🚧 백테스팅 엔진
- 🚧 실시간 시세 모니터링
- 🚧 포트폴리오 대시보드
- 🚧 알림 시스템

## 🐛 트러블슈팅

### Maven nexus 저장소 문제
```bash
# settings-override.xml 사용하여 Maven Central 저장소 사용
mvn -s settings-override.xml clean compile
```

### Java 버전 문제
```bash
# Java 17 설치 필요
java -version  # 17 이상 확인
```

### 포트 충돌
```yaml
# application.yml에서 포트 변경
server:
  port: 8081
```

## 🤝 기여하기

이슈와 PR은 언제나 환영합니다!

## 📄 라이선스

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 개발자

- GitHub: https://github.com/dltndls321
- Email: dltndls321@gmail.com

---

**⚠️ 투자 주의사항**: 이 시스템은 교육 및 연구 목적으로 개발되었습니다. 실제 투자에 사용할 경우 손실 위험이 있으므로 신중하게 판단하시기 바랍니다.