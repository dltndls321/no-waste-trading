# Trading Backend

Java Spring Boot 기반 메인 트레이딩 서버

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
│   │   │       └── api/           # 외부 API 연동
│   │   └── resources/
│   │       └── application.yml    # 애플리케이션 설정
│   └── test/
│       └── java/                  # 테스트 코드
├── pom.xml                         # Maven 설정
└── Dockerfile                      # 도커 이미지 설정
```

## 🛠 기술 스택
- Java 17
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL
- Redis (캐싱)
- OkHttp (HTTP 클라이언트)

## 📌 주요 기능
- 한국투자증권 KIS API 연동
- Claude AI 에이전트 관리
- 거래 실행 및 모니터링
- 포트폴리오 관리
- Rate Limiting 처리

## 🚀 시작하기
```bash
# 빌드
mvn clean package

# 실행
java -jar target/trading-backend-1.0.0.jar
```