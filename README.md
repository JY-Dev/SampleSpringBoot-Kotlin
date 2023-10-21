### 기술스택

- Spring Boot
- Kotlin
- Spring Data Jpa
- QueryDsl
- MySql

### 어플리케이션 실행 방법

- 도커 엔진 설치
- 도커 컴포즈 설치
- 도커 엔진 실행
- 아래 명령어 실행

```
$ cd project경로
$ sudo docker-compose -p application up
$ cd csv
// MySql Container 실행중일 때 입력
$ docker build -t csv .
$ docker run --network host -it csv
```

### API 호출 방법

- intellij http 파일 실행
    - 패키지 경로 : web/http
