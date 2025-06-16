# 💡 3팀 스터디 모집 플랫폼

스터디를 개설하고, 참가하고, 승인/거절하며  
관리할 수 있는 **스터디 모집 통합 서비스**입니다.

### ➡️ [Backend Source](https://github.com/backend20250319/BE09-3rd-3team)
: 스터디잇(StudyIt)

---
## 👥 팀원 소개 및 담당 기능

| 이름   | 담당 서비스                  | 주요 기능 예시                                                 |
|--------|-------------------------------|------------------------------------------------------------------|
| 임나연 | 사용자 서비스 (user-service)   | - 회원가입/로그인/로그아웃/회원탈퇴<br/>- 사용자 정보 수정(비밀번호, 이름)                        |
| 조석근 | 스터디 서비스 (study-service) | - 스터디 개설/조회/수정/삭제<br/>- 스터디 마감 처리            |
| 지정호 | 참여 서비스 (studyjoin-service) | - 스터디 참가 신청<br/>- 참가 승인/거절 처리                 |
| 박준범 | 공지/댓글 서비스 (notice-service) | - 공지사항 등록<br/>- 댓글 작성 및 조회                     |
| 이석진 | Gateway & Config 서버        | - 인증 토큰 전달<br/>- MSA 라우팅 설정                          |

---
## 1. 기획서


### 🎯 프로젝트 목적

- MSA 환경에서의 실전 협업 경험
- 사용자 인증 및 권한 기반 처리 흐름 학습
- FeignClient, Gateway, Config Server 등 Spring Cloud 기술 실습
- 실전 API 설계 및 Postman 테스트 경험 강화


### 🚀 기술 스택

![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring Boot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![AWS](https://img.shields.io/badge/AWS-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)

### 🧩 마이크로서비스 구조
| 모듈명           | 기능 역할                                                |
|------------------|-----------------------------------------------------------|
| config-server     | Spring Cloud Config 서버로, 중앙 설정 관리                      |
| discovery-server  | Eureka 같은 서비스 디스커리 서버 역할                          |
| gateway           | API Gateway, 라우팅/필터링/인증 처리 등 중간 허브 역할           |
| comment-service   | 댓글 기능 제공 (CRUD 처리, DB 접근 등 포함)                    |
| jwt-common        | 공통 JWT 유틸리티 또는 보안 관련 설정 공유                      |
| study-service     | 학습 컨텐츠, 진도, 기록 등 학습 관련 기능 관리                   |
| user-service      | 사용자 계정 관리, 로그인, 회원가입, 정보 수정 등의 사용자 기능 담당 |


---
## 2. 요구사항 정의서

---
## 🧻 3. 인터페이스 설계서
### 3-1. API 설계
## 📕 스터디 서비스
<details>
    <summary>📕 스터디 참가 신청 API</summary>
    
### 📤 요청 정보

- **메서드(Method)**: `POST`
- **URL**: `http://localhost:8080/study/join`
- **헤더(Headers)**:
    - `Content-Type: application/json`
    - `Authorization: Bearer {토큰}`

### 📦 요청 바디 (Request Body)

```json
{
  "studyRoomId": 1
}

```

| 필드명 | 타입 | 필수 여부 | 설명 |
| --- | --- | --- | --- |
| studyRoomId | integer | ✅ 필수 | 참가하려는 스터디의 고유 ID 값입니다 |

### 📥 응답 정보

응답은 JSON 형식이며, 아래와 같은 필드를 포함합니다.

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| success | boolean | 요청 성공 여부 (`true` 또는 `false`) |
| data | string | 응답 관련 데이터 또는 메시지 (성공 시 안내 메시지 등) |
| errorCode | string | 실패 시 반환되는 에러 코드 (성공 시 `null`) |
| message | string | 실패 사유에 대한 설명 메시지 (성공 시 `null`) |
| timestamp | string | 응답 시간 (ISO-8601 형식의 타임스탬프) |

---

### ✅ 성공 응답 예시

```json
{
  "success": true,
  "data": "스터디 참여 신청이 완료되었습니다.",
  "errorCode": null,
  "message": null,
  "timestamp": "2025-06-15T17:45:00.123"
}

```
### ❌ 실패 응답 예시 1 - 신청한 스터디에 재 신청 시

```json
{
  "success": false,
  "data": null,
  "errorCode": "DUPLICATE_STUDY",
  "message": "이미 신청한 스터디입니다.",
  "timestamp": "2025-06-15T17:45:12.456"
}

```

### ❌ 실패 응답 예시 2 - 존재하지 않는 StudyRoomId 값 입력 시

```json
{
    "success": false,
    "data": null,
    "errorCode": "STUDY_NOT_FOUND",
    "message": "스터디 ID : 123에 해당하는 스터디를 찾을 수 없습니다.",
    "timestamp": "2025-06-15T17:04:18.8901431"
}

```

### 📝 비고

- 인증된 사용자만 호출 가능합니다.
- 이미 신청한 스터디에 다시 신청할 경우 `DUPLICATE_STUDY` 에러가 반환됩니다.
- `studyRoomId` 값이 존재하는지 백엔드에서 확인합니다.
</details>

<details>

    <summary>📕 스터디 참가 신청 취소 API</summary>

### 📤 요청 정보

- **메서드(Method)**: `DELETE`
- **URL**: `http://localhost:8080/study/cancel/{id}`
- **인증 필요**: ✅ `Bearer 토큰` 필요 (로그인 유저 기준)

### 📌 경로 파라미터 (Path Parameter)

| 이름 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| id | Long | ✅ | 취소하려는 스터디의 고유 ID (`studyRoomId`) |

예: `DELETE http://localhost:8080/study/cancel/{studyRoomId}`

### ❌ 요청 바디 (Request Body)

- 없음 (Body 없이 요청합니다)

### 📥 응답 정보 (Response)

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| success | boolean | 요청 성공 여부 |
| data | string | 메시지 또는 결과 데이터 (`성공 시 취소 안내 메시지`) |
| errorCode | string | 실패 시 에러 코드 (`성공 시 null`) |
| message | string | 실패 시 상세 메시지 (`성공 시 null`) |
| timestamp | string | 응답 생성 시간 (ISO-8601 형식) |

### ✅ 성공 응답 예시

```json
{
  "success": true,
  "data": "스터디 신청이 성공적으로 취소되었습니다.",
  "errorCode": null,
  "message": null,
  "timestamp": "2025-06-15T17:50:23.456"
}

```

---

### ❌ 실패 응답 예시 1 — 신청 내역 없음

```json
{
  "success": false,
  "data": null,
  "errorCode": "STUDY_NOT_FOUND",
  "message": "해당 유저는 이 스터디에 신청한 내역이 없습니다.",
  "timestamp": "2025-06-15T17:51:01.789"
}

```

---

### ❌ 실패 응답 예시 2 — 상태가 대기(PENDING)가 아님

```json
{
  "success": false,
  "data": null,
  "errorCode": "INVALID_STATUS",
  "message": "대기 상태(PENDING)인 신청만 취소할 수 있습니다.",
  "timestamp": "2025-06-15T17:51:30.000"
}

```
### 📝 비고

- 이 API는 로그인한 사용자의 신청 내역 중 `대기 상태(PENDING)`인 것만 취소할 수 있습니다.
- 승인된 신청(예: `APPROVED`, `REJECTED`)은 취소할 수 없습니다.
- 스터디 ID는 존재해야 하며, 유효하지 않으면 `STUDY_NOT_FOUND` 오류가 발생합니다.

</details>
