# No Waste Trading 🤖💰

> Claude Max 플랜과 Java를 결합한 하이브리드 AI 자동매매 시스템

## 🎯 프로젝트 소개

비싼 Claude Max 구독을 최대한 활용하여 API 비용 없이 고급 AI 분석을 제공하는 자동매매 시스템입니다!

### 핵심 특징
- 💡 **토큰 활용**: Claude Max 플랜의 남는 토큰을 효율적으로 활용
- 🤖 **하이브리드 구조**: AI 판단은 Claude Code, 실행은 Java
- 📊 **자동화 분석**: 스케줄러를 통한 일일/주간 자동 분석
- 🛡️ **AI 리스크 관리**: Claude가 리스크 평가, Java가 실행

## 🏗️ 시스템 아키텍처

```
┌─────────────────────────────────────────────┐
│      Claude Code CLI (분석 레이어)           │
│  시장 분석 | 종목 발굴 | 포트폴리오 분석    │
├─────────────────────────────────────────────┤
│      Trading Backend (Java/Spring)           │
│  실시간 거래 | 리스크 관리 | 데이터 처리    │
├─────────────────────────────────────────────┤
│            External APIs                     │
│  KIS API | Market Data | News API           │
└─────────────────────────────────────────────┘
```

## 📁 프로젝트 구조

```
no-waste-trading/
├── trading-backend/     # Java Spring Boot 메인 서버
├── claude-agents/       # Claude Code 자동화 스크립트
│   ├── daily/          # 일일 분석 스크립트
│   ├── weekly/         # 주간 분석 스크립트
│   └── prompts/        # 에이전트별 프롬프트 템플릿
├── data/               # 분석 결과 저장소
│   ├── analysis/       # Claude 분석 결과
│   ├── signals/        # 매매 신호
│   └── reports/        # 일일/주간 리포트
├── scheduler/          # 스케줄러 설정 (cron/task)
├── backtest-engine/    # 백테스팅 엔진
├── frontend/           # 웹 UI (React)
├── docs/               # 프로젝트 문서
└── monitoring/         # 모니터링 설정
```

## 🛠 기술 스택

### Backend (실시간 처리)
- **Java 17** / Spring Boot 3.x
- **PostgreSQL** / Redis
- **한국투자증권 KIS API**

### AI 분석 (배치 처리)
- **Claude Code CLI** (Claude Max 플랜)
- **Python 3.10+** (데이터 전처리)
- **Windows Task Scheduler** / Cron

### Infrastructure
- **Docker** / Docker Compose
- **GitHub Actions** (CI/CD)
- **Grafana** / Prometheus (모니터링)

## 🤖 하이브리드 에이전트 시스템

| 에이전트 | 구현 방식 | 실행 주기 | 역할 |
|---------|-----------|-----------|------|
| Research Agent | Claude Code | 일 1회 (06:00) | 시장 동향, 뉴스 분석 |
| Analysis Agent | Claude Code | 일 2회 (06:30, 22:30) | 기술적/재무적 분석 |
| Portfolio Agent | Claude Code | 주 1회 (일요일) | 포트폴리오 리밸런싱 |
| Risk Agent | Claude Code | 수시 (신호 발생시) | 리스크 평가 및 판단 |
| Execution Module | Java | 실시간 | 주문 실행, 체결 관리 |
| Monitor Module | Java | 실시간 | 가격 추적, 조건 감시 |

## 🚀 시작하기

### 사전 요구사항
- Java 17+ (Spring Boot 3.x)
- Maven 3.6+
- Claude Code CLI 설치
- Python 3.10+ (선택사항)
- Redis (선택사항)
- PostgreSQL (운영환경용)

### Claude Code CLI 설치
```bash
# Windows (PowerShell)
iwr https://storage.googleapis.com/claude-code-cli/install.ps1 -useb | iex

# 로그인
claude login
```

### API 키 발급
1. **한국투자증권 KIS API**
   - [KIS Developers](https://apiportal.koreainvestment.com/) 가입
   - 모의투자 계좌 신청
   - 앱 등록 및 API 키 발급

### 설치 및 실행

1. **프로젝트 클론**
```bash
git clone https://github.com/dltndls321/no-waste-trading.git
cd no-waste-trading
```

2. **환경 변수 설정**
```bash
cp .env.example .env
# .env 파일 편집하여 KIS API 키 입력
# Claude API 키는 불필요 (Claude Code CLI 사용)
```

3. **백엔드 서버 실행**
```bash
cd trading-backend
mvn -s settings-override.xml spring-boot:run
```

4. **자동화 스크립트 설정**
```bash
# Windows Task Scheduler 설정
cd scheduler
./setup-windows-scheduler.bat

# 또는 수동 실행
cd claude-agents/daily
./run-morning-analysis.bat
```

## 📅 일일 자동화 스케줄

```
06:00 - 시장 분석 (Claude Code)
  └─> data/analysis/daily_research.json

06:30 - 매매 신호 생성 (Claude Code)
  └─> data/signals/trading_signals.json

07:00-22:00 - 실시간 거래 (Java)
  ├─> 신호 기반 자동 매매
  ├─> 리스크 모니터링
  └─> 주문 체결 관리

22:30 - 종가 분석 (Claude Code)
  └─> data/analysis/closing_analysis.json

23:00 - 일일 리포트 (Claude Code)
  └─> data/reports/daily_report.json

일요일 22:00 - 주간 포트폴리오 리밸런싱 (Claude Code)
  └─> data/analysis/weekly_rebalance.json
```

## 📊 성과 목표

- 샤프 비율 > 1.0
- 연환산 수익률 > 15%
- 최대 낙폭 < 15%
- 월 평균 거래 횟수: 20-30회
- **추가 API 비용: $0** (Claude Max 플랜 토큰 활용)

## 📈 개발 로드맵

### Phase 1: 기반 구축 ✅
- [x] 프로젝트 구조 설계
- [x] Spring Boot 백엔드 구축
- [x] 환경별 설정 분리
- [x] 하이브리드 아키텍처 재설계
- [x] KIS API 키 발급 및 설정

### Phase 2: Spring Boot 서버 연동 ✅
- [x] 환경변수 설정 문제 해결
- [x] application.yml 하이브리드 구조 수정
- [x] Spring Boot 서버 정상 기동
- [x] KIS API 기본 호출 구조 완료
- [ ] KIS API OAuth 토큰 인증 완성

### Phase 3: Claude Code 통합 🚧  
- [ ] Claude Code 자동화 스크립트 작성
- [ ] 프롬프트 템플릿 최적화
- [ ] 스케줄러 설정
- [ ] JSON 파싱 및 검증

### Phase 4: 실시간 처리
- [ ] Java 파일 모니터링 시스템
- [ ] 실시간 주문 실행 시스템
- [ ] Java 리스크 관리 모듈
- [ ] 알림 시스템

### Phase 5: 백테스팅 & 검증
- [ ] Claude Code 기반 백테스팅
- [ ] 과거 데이터 분석
- [ ] 모의투자 테스트

### Phase 6: 운영 자동화
- [ ] 웹 대시보드
- [ ] 성과 모니터링
- [ ] 자동 리포트 생성

## 🔧 Claude Code 스크립트 예시

```batch
# claude-agents/daily/morning-analysis.bat
@echo off
set TODAY=%date:~0,10%

echo Running morning market analysis...
claude "오늘 %TODAY% 미국 시장 분석: 
1. 주요 뉴스와 이벤트 정리
2. 섹터별 동향 분석  
3. 관심 종목 10개 추천
4. 각 종목별 매수/매도 신호
결과를 JSON 형식으로 작성해줘" > ..\..\data\analysis\daily_research_%TODAY%.json

echo Analysis complete!
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