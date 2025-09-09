# Trading Backend

Java Spring Boot 기반 실시간 트레이딩 서버 (하이브리드 시스템의 핵심)

## 📋 역할

- **실시간 처리**: 주문 실행, 체결 관리, 리스크 모니터링
- **데이터 관리**: Claude Code 분석 결과 파싱 및 저장
- **API 통신**: KIS API 직접 호출
- **파일 모니터링**: Claude Code가 생성한 JSON 파일 감지

## 📁 프로젝트 구조

```
trading-backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/tokentrader/
│   │   │       ├── controller/    # REST API 컨트롤러
│   │   │       ├── service/       # 비즈니스 로직
│   │   │       ├── model/         # 도메인 모델
│   │   │       ├── config/        # 설정 클래스
│   │   │       ├── api/           # KIS API 연동
│   │   │       ├── monitor/       # 파일 모니터링
│   │   │       └── execution/     # 실시간 거래 실행
│   │   └── resources/
│   │       └── application.yml    # 애플리케이션 설정
│   └── test/
│       └── java/                  # 테스트 코드
├── pom.xml                         # Maven 설정
└── settings-override.xml           # Maven 저장소 우회 설정
```

## 🛠 기술 스택

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL (운영)
- H2 Database (개발)
- Redis (선택사항 - 캐싱)
- OkHttp (HTTP 클라이언트)

## 📌 주요 기능

### 실시간 처리 (Java)
- **Risk Agent**: 실시간 손절/익절 모니터링
- **Execution Agent**: 주문 실행 및 체결 관리
- **파일 모니터**: Claude Code 결과 파일 감지

### API 연동
- 한국투자증권 KIS API 직접 호출
- Rate Limiting (초당 15회)
- 모의투자/실거래 환경 분리

### 데이터 처리
- JSON 파일 파싱 (Claude Code 결과)
- 거래 신호 처리
- 포트폴리오 상태 관리

## 🚀 시작하기

### 환경 변수 설정 (.env)
```bash
# KIS API (모의투자)
KIS_MOCK_APP_KEY=your_app_key
KIS_MOCK_SECRET_KEY=your_secret_key
KIS_MOCK_ACCOUNT_NUMBER=12345678
KIS_MOCK_ACCOUNT_PRODUCT_CODE=01

# Database
DB_USERNAME=tradinguser
DB_PASSWORD=tradingpass
```

### 빌드 및 실행
```bash
# Maven 빌드 (nexus 우회)
mvn -s settings-override.xml clean compile

# 개발 모드 실행
mvn -s settings-override.xml spring-boot:run

# 프로덕션 빌드
mvn -s settings-override.xml clean package

# JAR 실행
java -jar target/trading-backend-1.0.0.jar
```

## 📊 파일 모니터링 구조

```
data/
├── analysis/
│   ├── daily_research_*.json      # 매일 06:00
│   └── closing_analysis_*.json    # 매일 22:30
├── signals/
│   └── trading_signals_*.json     # 매일 06:30
└── reports/
    └── daily_report_*.json        # 매일 23:00
```

Java의 `WatchService`가 위 디렉토리를 모니터링하며, 새 파일 생성 시:
1. JSON 파싱
2. 신호 검증
3. 리스크 체크
4. 주문 실행

## 🔧 핵심 컴포넌트

### FileMonitorService
```java
// Claude Code가 생성한 JSON 파일 감지
@Component
public class FileMonitorService {
    @EventListener
    public void onFileCreated(Path file) {
        // JSON 파싱 → 거래 신호 처리
    }
}
```

### RiskManagementService
```java
// 실시간 리스크 모니터링
@Service
public class RiskManagementService {
    // 손절/익절 라인 체크
    // 포지션 사이즈 계산
}
```

### OrderExecutionService
```java
// KIS API를 통한 주문 실행
@Service
public class OrderExecutionService {
    // 주문 타이밍 최적화
    // 슬리피지 최소화
}
```

## 📈 API 엔드포인트

- `GET /api/portfolio` - 현재 포트폴리오 조회
- `GET /api/signals` - 활성 거래 신호 조회
- `POST /api/execute` - 수동 주문 실행
- `GET /api/risk/status` - 리스크 상태 조회
- `GET /actuator/health` - 서버 상태 체크

## 🔐 보안 설정

- API 키는 환경 변수로 관리
- 실거래/모의투자 프로파일 분리
- Rate Limiting 적용

## 📝 로그 설정

```yaml
logging:
  level:
    com.tokentrader: DEBUG
    com.tokentrader.api.kis: INFO
  file:
    name: logs/trading-backend.log
```

## 🐛 트러블슈팅

### Maven nexus 저장소 문제
```bash
mvn -s settings-override.xml clean compile
```

### Redis 연결 실패 (선택사항)
Redis는 필수가 아니므로 연결 실패해도 서버는 정상 작동

### 포트 충돌
```yaml
# application.yml
server:
  port: 8081  # 기본 8080에서 변경
```