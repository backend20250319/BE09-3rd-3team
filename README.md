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
### ➡️ [요구사항 정의서 & 테스트 케이스 결과서](https://docs.google.com/spreadsheets/d/1HtXuEdEVc-X33P9dlSijZ4n9vdVsh1qGxlXfCI01yM8/edit?usp=sharing)


---


## 🧻 3. 인터페이스 설계서
### 3-1. API 설계

### 👤 회원가입 서비스
<details>
    <summary>📌 사용자 회원가입 API</summary>

### 📤 요청 정보

- **HTTP 메서드**: `POST`
- **URL**: `http://localhost:8080/user/signup`
- **Content-Type**: `application/json`

### 📦 요청 바디 (Request Body)

```json
{
  "userId": "user12",
  "password": "user12",
  "name": "user12"
}

```

| 필드명 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| userId | string | ✅ | 사용자 고유 ID. 로그인 시 사용되며 시스템 내에서 중복될 수 없음 |
| password | string | ✅ | 사용자 계정 비밀번호. 보안상 암호화되어 저장되어야 함 |
| name | string | ✅ | 사용자 실명 또는 닉네임. 사용자 프로필 등에 노출될 수 있음 |

### 📥 응답 정보

- **HTTP 상태코드**: `201 Created`
- **Content-Type**: `application/json`

### 📄 응답 바디 구조

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| success | boolean | 요청이 성공했는지 여부 (`true` 또는 `false`) |
| data | null | 현재 사용되지 않으며 향후 확장을 위해 예약된 필드 |
| errorCode | null | 오류 발생 시 코드가 입력됨. 성공 시에는 `null` |
| message | string | 안내 또는 오류 메시지. 성공 시에는 빈 문자열 또는 간단 메시지 |
| timestamp | string | 응답 생성 시간 (ISO 8601 형식 문자열) |

---

### ✅ 성공 응답 예시

```json
{
  "success": true,
  "data": null,
  "errorCode": null,
  "message": "",
  "timestamp": "2025-06-15T18:55:00.000"
}

```


### ❌ 실패 예시 -1 (중복된 userId)

```json
{
  "success": false,
  "data": null,
  "errorCode": "DUPLICATE_USER",
  "message": "이미 존재하는 사용자 ID 입니다.",
  "timestamp": "2025-06-15T18:55:30.123"
}

```

### ❌ 실패 예시 -2 (필드값 공백)

```json
{
  "success": false,
  "data": null,
  "errorCode": "INVALID_USER_ID", // INVALID_PASSWORD, INVALID_NAME
  "message": "아이디는 필수 입력값입니다.", // 비밀번호는 필수 입력 항목입니다. , 이름은 필수 입력 항목입니다.
  "timestamp": "2025-06-15T18:55:30.123"
}
```


###📝 참고 사항

- `userId`는 반드시 고유해야 하며, 중복된 경우 400 오류 또는 사용자 정의 오류 코드가 반환됩니다.
- 비밀번호는 절대 평문으로 저장되어서는 안 되며, 반드시 해시 암호화 처리가 필요합니다.
- 보안을 위해 최소 비밀번호 정책 및 유효성 검사를 서버 또는 클라이언트 단에서 추가하는 것이 좋습니다.
</details>

<details>
    <summary>📌 사용자 로그인 API</summary>
## 📤 요청 정보

- **HTTP 메서드**: `POST`
- **URL**: `http://localhost:8080/user/login`
- **Content-Type**: `application/json`

### 📦 요청 바디 (Request Body)

```json
{
  "userId": "user08",
  "password": "user08"
}
```

| 필드명 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| userId | string | ✅ | 로그인 대상 사용자 ID |
| password | string | ✅ | 해당 사용자 ID에 대한 비밀번호 |


## 📥 응답 정보

- **Content-Type**: `application/json`

### 응답 구조

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| success | boolean | 로그인 성공 여부 |
| data | object | 로그인 성공 시 토큰 정보를 포함하는 객체 |
| ┗ accessToken | string | API 인증을 위한 액세스 토큰 (Bearer Token 등) |
| ┗ refreshToken | string | 액세스 토큰 만료 시 재발급 요청에 사용되는 토큰 |
| errorCode | string or null | 실패 시 에러 코드, 성공 시에는 null |
| message | string | 로그인 처리 결과에 대한 메시지 |
| timestamp | string | 응답이 생성된 시간 (ISO-8601 형식) |

### ✅ 로그인 성공 응답 예시

```json
{
  "success": true,
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "d4b9ef3a-d2e4-4c77-bcc1-3f8c304b3d10"
  },
  "errorCode": null,
  "message": "",
  "timestamp": "2025-06-15T19:20:00.000"
}
```

### ❌ 로그인 실패 예시

```json
{
  "success": false,
  "data": null,
  "errorCode": "INVALID_CREDENTIALS",
  "message": "아이디 또는 비밀번호가 올바르지 않습니다.",
  "timestamp": "2025-06-15T19:21:12.000"
}
```
</details>


<details>
    <summary>📌 사용자 비밀번호 변경 API</summary>

## 📤 요청 정보

- **HTTP 메서드**: `PATCH`
- **URL**: `http://localhost:8080/user/{username}/password`
- **Content-Type**: `application/json`
- **인증 필요**: ✅ 로그인 필요 (본인만 가능)

### 🔧 경로 변수 (Path Variable)

| 변수명 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| username | string | ✅ | 비밀번호를 변경할 사용자 ID |


### 📦 요청 바디 (Request Body)

```json
{
  "currentPassword": "user12",
  "newPassword": "user13"
}
```

| 필드명 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| currentPassword | string | ✅ | 현재 사용자의 비밀번호 (본인 인증용) |
| newPassword | string | ✅ | 새로 설정할 비밀번호 (서버의 비밀번호 정책 적용) |


## 📥 응답 정보

### 📄 성공 응답 구조

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| success | boolean | 비밀번호 변경 성공 여부 |
| data | object | 변경 전후 비밀번호 요약 정보 (`before`, `after` 등) |
| errorCode | string | 실패 시 오류 코드, 성공 시 `null` |
| message | string | 안내 또는 실패 메시지 |
| timestamp | string | 응답 시간 (ISO 8601 형식) |


### ✅ 성공 응답 예시

```json
{
  "success": true,
  "data": {
    "before": "******",
    "after": "user13"
  },
  "errorCode": null,
  "message": "비밀번호가 성공적으로 변경되었습니다.",
  "timestamp": "2025-06-15T19:55:00.000"
}
```

> ⚠ 실제 비밀번호를 그대로 노출하지 않고 "****" 또는 비밀번호 길이, 변경 여부 정도만 요약해서 반환하는 것이 보안상 안전합니다.

### ❌ 실패 응답 예시

### 1. 사용자를 찾을 수 없는 경우

- **Status Code**: `400 Bad Request`
- **Content-Type**: `application/json`

```json
"해당 사용자를 찾을 수 없습니다."
```
### 2. 현재 비밀번호가 일치하지 않는 경우

```json
"현재 비밀번호가 올바르지 않습니다."
```
    
</details>

<details>
    <summary>📌 사용자 이름 변경 API</summary>

### 📤 요청 정보

- **HTTP 메서드**: `PATCH`
- **URL**: `http://localhost:8080/user/{userId}/name`
- **Content-Type**: `application/json`
- **인증 필요**: ✅ 로그인된 사용자만 가능 (보통 본인만 가능)

### 🔧 경로 변수 (Path Variable)

| 이름 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| userId | string | ✅ | 이름을 변경할 대상 사용자 ID |


### 📦 요청 바디 (Request Body)

| 필드명 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| name | string | ✅ | 새로 설정할 사용자 이름 |

### 📥 응답 정보

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| success | boolean | 요청 성공 여부 |
| data | null | 현재는 사용되지 않음 |
| errorCode | string | 오류 발생 시 반환되는 에러 코드 (성공 시 `null`) |
| message | string | 결과에 대한 메시지 |
| timestamp | string | 응답 생성 시간 (ISO 8601 형식) |

### ✅ 성공 응답 예시

```json
{
  "success": true,
  "data": null,
  "errorCode": null,
  "message": "이름이 성공적으로 변경되었습니다.",
  "timestamp": "2025-06-15T19:45:00.000"
}
```

### ❌ 실패 응답 예시 — 사용자 없음

- **HTTP 상태 코드**: `400 Bad Request`
- **Content-Type**: `application/json`

```json

  "해당 사용자를 찾을 수 없습니다."
```

### 📝 참고 사항

- 요청자는 보통 본인이어야 하며, 다른 사용자의 이름은 변경할 수 없습니다.
- 존재하지 않는 `userId`로 요청 시 400 상태 코드와 함께 `"해당 사용자를 찾을 수 없습니다."`라는 메시지를 반환합니다.
- 이름에 대해 공백 또는 최대 길이 제한 등의 유효성 검사가 포함될 수 있습니다.
</details>



<details>
    <summary>📌 사용자 로그아웃 API</summary>

### 📤 요청 정보

- **HTTP 메서드**: `POST`
- **URL**: `http://localhost:8080/user/logout`
- **Content-Type**: `application/json`
- **인증 필요**: ✅ 로그인 상태에서 사용 (일반적으로 AccessToken 함께 전달됨)


### 📦 요청 바디 (Request Body)

```json
{
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ..."
}
```

| 필드명 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| refreshToken | string | ✅ | 현재 사용자의 세션에 발급된 리프레시 토큰 |


### 📥 응답 정보

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| success | boolean | 요청 성공 여부 |
| data | null | 로그아웃 처리이므로 데이터는 `null` |
| errorCode | string | 실패 시 오류 코드 (`INVALID_TOKEN`, `UNAUTHORIZED`) 등 |
| message | string | 결과 메시지 |
| timestamp | string | 응답 생성 시각 (ISO 8601 형식) |

### ✅ 성공 응답 예시 (`200 OK`)

```json
{
  "success": true,
  "data": null,
  "errorCode": null,
  "message": "로그아웃이 성공적으로 완료되었습니다.",
  "timestamp": "2025-06-15T20:05:00.000"
}

```

### ❌ 실패 응답 예시 — 잘못된 또는 만료된 토큰 (`401 Unauthorized`)

```json
{
  "error": "Unauthorized",
  "message": "Full authentication is required to access this resource"
}
```
</details>


<details>
    <summary>📌 사용자 삭제 API</summary>

### 📤 요청 정보

- **HTTP 메서드**: `DELETE`
- **URL**: `http://localhost:8080/user/{username}/delete`
- **인증 필요**: ✅ 로그인된 사용자
- **Content-Type**: 없음 (Body 필요 없음)

### 🔧 경로 변수 (Path Variable)

| 변수명 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| username | string | ✅ | 삭제 대상 사용자의 고유 ID |

> 예:
> 
> 
> `DELETE http://localhost:8080/user/user12/delete`
> 

### 📥 응답 정보

응답은 JSON 형식이며 다음과 같은 구조를 가집니다:

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| success | boolean | 요청 성공 여부 (`true` or `false`) |
| data | null | 삭제 작업이므로 일반적으로 `null` 반환 |
| errorCode | string | 실패 시 반환되는 에러 코드 (성공 시 `null`) |
| message | string | 처리 결과에 대한 설명 메시지 |
| timestamp | string | 응답 생성 시각 (ISO 8601 형식) |

### ✅ 사용자 삭제 성공 응답 예시

```json
{
  "success": true,
  "data": null,
  "errorCode": null,
  "message": "회원 탈퇴가 완료되었습니다.",
  "timestamp": "2025-06-15T20:10:00.000"
}
```

### ❌ 실패 응답 예시 1 — 사용자가 존재하지 않음

- **HTTP 상태 코드**: `400 Bad Request`
- **Content-Type**: `application/json`

```json
  "해당 사용자를 찾을 수 없습니다."
```

### ❌ 실패 응답 예시 2 — 본인이 아닌 사용자 요청

```json
{
  "success": false,
  "data": null,
  "errorCode": "UNAUTHORIZED",
  "message": "본인만 탈퇴할 수 있습니다.",
  "timestamp": "2025-06-15T20:12:00.000"
}
```

### 📝 참고 사항

- `username`은 시스템 내에서 실제로 존재하는 사용자여야 합니다.
- 본인이 아닌 계정을 삭제하려는 경우 `403 Forbidden` 또는 `400 Bad Request`가 반환될 수 있습니다.
- 삭제 처리는 보통 논리 삭제(soft delete) 또는 물리 삭제 중 정책에 따라 다를 수 있습니다.
</details>


### 📕 스터디 서비스
<details>
    <summary>📌 스터디 참가 신청 API</summary>
    
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
    <summary>📌 스터디 참가 신청 취소 API</summary>

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


<details>
    <summary>📌 내 스터디 신청 내역 조회 API</summary>
    
### 📤 요청 정보

- **메서드(Method)**: `GET`
- **URL**: `http://localhost:8080/study/me`
- **인증 필요**: ✅ `Bearer 토큰` 필요 (로그인된 사용자 기준)

### 📥 응답 정보

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| success | boolean | 요청 성공 여부 (`true` 또는 `false`) |
| data | array 또는 string | 사용자의 스터디 신청 내역 리스트 (`없으면 빈 문자열 ""`) |
| errorCode | string 또는 null | 실패 시 에러 코드 (성공 시 `null`) |
| message | string 또는 null | 실패 또는 안내 메시지 (성공 시 `null`) |
| timestamp | string | 응답 시간 (ISO-8601 형식) |

### 🔍 data 내부 구조 (성공 시 array)

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| id | integer | 신청 내역 고유 ID |
| studyRoomId | integer | 신청한 스터디룸의 ID |
| title | string | 스터디 제목 |
| description | string | 스터디 설명 |
| category | string | 카테고리 |
| status | string | 신청 상태 (`PENDING`, `APPROVED` 등) |
| createdAt | string | 신청 일시 |

### ✅ 예시 응답 (내역 존재 시)

```json
{
  "success": true,
  "data": [
    {
      "id": 12,
      "studyRoomId": 101,
      "title": "자바 스터디",
      "description": "초급 자바 프로그래밍 공부",
      "category": "프로그래밍",
      "status": "PENDING",
      "createdAt": "2025-06-10T14:32:45.000"
    }
  ],
  "errorCode": null,
  "message": null,
  "timestamp": "2025-06-15T18:10:22.123"
}

```

### ✅ 예시 응답 (내역 없음)

```json
{
  "success": true,
  "data": "신청한 스터디가 없습니다.",
  "errorCode": null,
  "message": null,
  "timestamp": "2025-06-15T18:12:00.789"
}

```

### 📝 비고

- 반환되는 스터디 신청 상태는 예: `PENDING`, `APPROVED`, `REJECTED` 등이 될 수 있습니다.
- 이 API는 사용자 개인의 스터디 활동을 효과적으로 관리하기 위해 유용합니다.
</details>

<details>
    <summary>📌 스터디 마감 API</summary>

### 📤 요청 정보

- **HTTP 메서드**: `PUT`
- **URL**: `http://localhost:8080/study/close/{studyRoomId}`
- **Content-Type**: 없음
- **인증 필요**: ✅ 로그인 필요 (스터디 생성자 또는 관리자만 허용)

### 🔧 경로 변수 (Path Parameter)

| 변수명 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| studyId | integer | ✅ | 종료하려는 스터디의 고유 ID |

예시 요청:

`PUT http://localhost:8080/study/close/5`

### 📥 응답 정보

### ✅ 성공 시 (200 OK)

```json
[]

### ❌ 실패 시 (404 Not Found 등)

```json
{
  "error": "Study Not Found",
  "message": "해당 스터디룸을 찾을수 없습니다. id=100",
  "timestamp": "2025-06-16T12:36:19.686847",
  "status": 404
}
```

### 📝 참고 사항

- 이 API는 스터디룸이 `OPEN` 상태일 때만 마감이 가능합니다. 이미 `CLOSED` 상태이면 중복 마감 요청을 방지해야 합니다.
- 마감된 스터디는 이후 신청, 수정이 제한되며, **읽기 전용** 상태로 전환됩니다.
- 마감 일시는 `closedAtFormatted` 필드로 별도 저장되거나 응답에 포함될 수 있습니다.
</details>


<details>
    <summary>📌 스터디장 스터디 OPEN 상태 조회 API</summary>
    
### 📤 요청 정보

- **HTTP 메서드**: `GET`
- **URL**: `http://localhost:8080/study/statuses/user/{userId}/open`

### 📌 경로 변수 (Path Parameters)

| 이름 | 타입 | 설명 |
| --- | --- | --- |
| `userId` | string | 오픈된 스터디 상태를 조회할 사용자의 고유 ID (예: `user100`) |

### 📤 응답

요청에 성공하면, 해당 사용자가 개설한 **오픈 상태의 스터디 목록**을 JSON 배열 형식으로 반환합니다.

### ✅ 응답 구조 (Array of Objects)

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| `studyRoomId` | integer | 스터디 고유 ID |
| `organizerId` | string | 스터디 개설자 ID |
| `userId` | string | 스터디에 연관된 사용자 ID |
| `status` | string | 스터디 상태 (예: `OPEN`) |

### 📘 응답 예시

### ▶ 스터디가 존재할 경우(200 OK):

```json
[
  {
    "studyRoomId": 1,
    "organizerId": "user08",
    "userId": "user08",
    "status": "OPEN"
  }
]
```

### ▶ 오픈된 스터디가 없을 경우:

```json
[]
```

### 📌 요약

이 엔드포인트는 사용자가 현재 참여하거나 개설한 **오픈 상태의 스터디 목록을 확인할 때** 유용합니다.

응답은 사용자의 활성 스터디 목록을 나열하거나, 오픈된 스터디가 없을 경우 빈 배열을 반환합니다.
    
</details>


<details>
    <summary>📌 스터디장 스터디 CLOSED 상태 조회 API</summary>

### 📌 엔드포인트

- **HTTP 메서드**: `GET`
- **URL**: `http://localhost:8080/study/statuses/user/{userId}/closed`

### 📄 설명

이 엔드포인트는 특정 사용자(`userId`)의 **종료된(Closed)** 스터디 상태를 조회하는 데 사용됩니다.

즉, 더 이상 활성화되지 않은 스터디(마감된 스터디)에 대한 정보를 가져옵니다.

### 📥 요청 파라미터

| 이름 | 위치 | 타입 | 설명 |
| --- | --- | --- | --- |
| `userId` | 경로 변수(Path) | string | 스터디 상태를 조회할 사용자의 고유 ID (예: `user100`) |

### 📤 응답 형식

응답은 JSON 배열 형식이며, 사용자의 **종료된 스터디 상태 객체 목록**을 포함합니다.

각 객체는 아래와 같은 필드를 가질 수 있습니다:

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| `studyRoomId` | integer | 스터디 고유 ID |
| `organizerId` | string | 스터디 개설자 ID |
| `userId` | string | 스터디에 연관된 사용자 ID |
| `status` | string | 스터디 상태 (`CLOSED`) |

### 📘 응답 예시

### ▶ 스터디가 존재할 경우:

```json
[
  {
    "studyRoomId": 2,
    "organizerId": "user08",
    "userId": "user08",
    "status": "CLOSED"
  }
]
```

### ▶ 종료된 스터디가 없을 경우:

```json
[]
```

### 📌 비고

- 응답은 스터디 상태 객체 배열이거나,
- 사용자가 종료한 스터디가 없을 경우에는 **빈 배열**을 반환합니다.
    
</details>

<details>
    <summary>📌 스터디장 스터디 전체 조회 API</summary>

### 📥 요청 정보

- **HTTP 메서드**: `GET`
- **URL**: `http://localhost:8080/study/statuses/user/{userId}`
- **경로 파라미터**:
    - `userId` (문자열): 상태를 조회할 대상 사용자의 ID

---

### 📤 응답 정보

응답은 **JSON 형식의 배열**로 반환되며, 해당 사용자와 관련된 모든 스터디 상태 정보를 포함합니다.

구조는 다음과 같습니다:

### ✅ 성공 응답 (200 OK)

- **응답 내용**:

```json

[
  {
    "studyRoomId": 0,
    "organizerId": "organizer01",
    "userId": "user02",
    "status": "approved"
  }
]

```

- 각 객체는 다음 정보를 포함합니다:
    - `studyRoomId` (정수): 스터디 방의 고유 ID
    - `organizerId` (문자열): 방을 개설한 조직자 ID
    - `userId` (문자열): 상태를 조회한 사용자 ID
    - `status` (문자열): 해당 스터디에서 사용자의 현재 상태 (`waiting`, `approved`, `rejected` 등)

### ❌ 실패 응답 ( 200 OK + 빈 배열)

- 사용자의 스터디 상태 정보가 **없을 경우**, 빈 배열이 반환됩니다:

```json

[]

```

이것은 다음을 의미할 수 있습니다:

- 해당 사용자가 존재하지 않거나
- 현재 어떤 스터디에도 속하지 않음

### 💡 비고

- `userId`가 정확하고 유효한 값인지 확인하세요.
- 이 엔드포인트는 **사용자의 스터디 참여 상태를 효과적으로 추적**하고 관리하는 데 유용합니다.
- JSON 응답 구조는 구현에 따라 약간 변경될 수 있습니다.
    
</details>

<details>
    <summary>📌 스터디룸 삭제 API</summary>

### 📤 요청 정보

- **HTTP 메서드**: `DELETE`
- **URL**: `http://localhost:8080/study/delete/{studyRoomId}`
- **Content-Type**: 없음
- **인증 필요**: ✅ 로그인 필요 (스터디 개설자 또는 관리자 권한 필요)

---

### 🔧 경로 변수 (Path Parameter)

| 이름 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| studyRoomId | integer | ✅ | 삭제할 스터디룸의 고유 ID 값 |

예시:

`DELETE http://localhost:8080/study/delete/3`


### 📥 응답 정보

| HTTP 상태 코드 | 설명 |
| --- | --- |
| `204 No Content` | 스터디 삭제 성공. 본문 없이 상태 코드만 반환됨 |
| `404 Not Found` | 해당 ID의 스터디룸이 존재하지 않음. 오류 메시지를 포함한 JSON 반환 |

### ✅ 삭제 성공 응답 예시

- **Status Code**: `204 No Content`
- **Body**: 없음

### ❌ 삭제 실패 응답 예시 (존재하지 않는 studyRoomId)

- **Status Code**: `404 Not Found`
- **Content-Type**: `application/json`

```json
{
    "error": "스터디 상태 레코드를 찾을 수 없습니다. id=
}
```

### 📝 참고 사항

- 이 요청은 스터디룸이 실제로 존재하고, 사용자가 해당 스터디의 **삭제 권한을 보유**해야만 성공합니다.
- 삭제된 스터디룸은 복구되지 않으며, 관련 신청 내역이나 활동 기록도 함께 무효화될 수 있습니다.
- 프론트엔드에서는 `204` 응답을 받으면 목록에서 해당 스터디를 제거하고, `404` 응답 시 사용자에게 “존재하지 않는 스터디입니다.” 등의 알림을 제공해야 합니다.

</details>

<details>
    <summary>📌 스터디룸 수정 API</summary>

### 📤 요청 정보

- **HTTP 메서드**: `PUT`
- **URL**: `http://localhost:8080/study/update/{studyRoomId}`
- **Content-Type**: `application/json`
- **인증 필요**: ✅ 로그인 필요 (스터디 개설자 또는 관리자 권한)

### 📦 요청 바디 예시

```json
{
  "title": "기본부터 시작하는 JPA!!",
  "description": "초보자 대상으로 하는 JPA 학습입니다.",
  "category": "#백엔드#BackEnd#풀스택",
  "maxMembers": 10
}
```

| 파라미터 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| title | string | ✅ | 수정할 스터디 제목 |
| description | string | ✅ | 수정할 스터디 설명 |
| category | string | ✅ | 해시태그 또는 분류 문자열 |
| maxMembers | integer | ✅ | 최대 모집 인원 |

### 📥 응답 정보

- **HTTP 상태 코드**: `200 OK` (성공 시) / `400 Bad Request` (에러 시)
- **Content-Type**: `application/json`

### ✅ 성공 응답 예시

```json
{
  "studyRoomId": 2,
  "title": "기본부터 시작하는 JPA!!",
  "description": "초보자 대상으로 하는 JPA 학습입니다.",
  "organizer": "홍길동",
  "status": "OPEN",
  "category": "#백엔드#BackEnd#풀스택",
  "maxMembers": 10,
  "createdAtFormatted": "2025-06-01 10:00",
  "closedAtFormatted": null
  }
```

### ❌ 실패 응답 예시 - 1 (존재하지 않는 스터디룸 수정 요청 시)

- **상태 코드**: `400 Bad Request`

```json
{
    "error": "스터디 상태 레코드를 찾을 수 없습니다. id=133"
}
```

### ❌ 실패 응답 예시 - 2 (Title 공백 수정 시)

```json
{
    "error": "Invalid Study Request",
    "message": "수정할 제목은 비어 있을 수 없습니다.",
    "timestamp": "2025-06-16T12:15:00.2349671",
    "status": 400
}
```

### ❌ 실패 응답 예시 - 3 (maxMembers 값이 0 일때)

```json
{
    "error": "Invalid Study Request",
    "message": "최대 인원은 1명 이상이어야 합니다.",
    "timestamp": "2025-06-16T12:17:50.6740241",
    "status": 400
}
```

### 📝 참고 사항

- `maxMembers`는 1 이상이어야 하며, 서버 측에서 유효성 검사 필요
- `category`는 클라이언트에서 `#태그1#태그2` 형식으로 전송, 백엔드에서는 분리 가능
- 수정 후 응답 객체는 생성 시와 동일한 구조를 유지하며, `studyRoomId`를 기준으로 변경된 정보를 확인 가능
  
</details>


<details>
    <summary>📌 스터디룸 상세 조회 API</summary>
    
### 📤 요청 정보

- **HTTP 메서드**: `GET`
- **URL**: `http://localhost:8080/study/search/{studyRoomid}`
- **Content-Type**: 없음
- **인증 필요**: ✅ 로그인된 사용자 (예: JWT 토큰)

### 🔧 경로 변수 (Path Parameter)

| 이름 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| id | integer | ✅ | 조회할 스터디룸의 고유 ID |

예시:

`GET http://localhost:8080/study/search/1`

### 📥 응답 정보

- **성공 시 상태 코드**: `200 OK`
- **실패 시 상태 코드**: `404 Not Found`
- **Content-Type**: `application/json`

### 📄 성공 응답 구조

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| studyRoomId | integer | 스터디룸의 고유 ID |
| title | string | 스터디 제목 |
| description | string | 스터디 설명 |
| organizer | string | 주최자 이름 |
| status | string | 현재 상태 (`OPEN`, `CLOSED`, `FULL` 등) |
| category | string | 카테고리 또는 태그 |
| maxMembers | integer | 최대 참여 인원 수 |
| createdAtFormatted | string | 생성일시 (YYYY-MM-DD HH:mm 형식) |
| closedAtFormatted | string or null | 종료일시 (종료 전이면 `null`) |

### ✅ 성공 응답 예시

```json
{
  "studyRoomId": 133,
  "title": "React 기초 스터디",
  "description": "리액트 기본 문법과 프로젝트 실습",
  "organizer": "박개발",
  "status": "OPEN",
  "category": "#프론트엔드",
  "maxMembers": 8,
  "createdAtFormatted": "2025-06-10 18:00",
  "closedAtFormatted": null
  }
```

### ❌ 실패 응답 예시 — ID에 해당하는 스터디룸이 없는 경우

- **Status**: `404 Not Found`
- **Content-Type**: `application/json`

```json
{
  "error": "해당 스터디룸을 찾을 수 없습니다. id=133"
}
```
### 📝 참고 사항

- 존재하지 않는 `studyRoomId`로 요청할 경우 `404 Not Found` 응답이 반환됩니다.
- 이 API는 주로 스터디룸 목록에서 특정 항목을 클릭했을 때 **상세 페이지 조회** 용도로 사용됩니다.
- 프론트엔드에서는 실패 응답을 받아 **"존재하지 않는 스터디입니다."** 등의 메시지로 사용자에게 안내해야 합니다.
    
</details>


<details>
    <summary>📌 전체 스터디룸 조회 API</summary>

### 📤 요청 정보

- **HTTP 메서드**: `GET`
- **URL**: `http://localhost:8080/study/searchAll`
- **Content-Type**: 없음 (Request Body 없음)
- **인증 필요**: ✅ 로그인된 사용자 (예: JWT 토큰)

### 📥 응답 정보

- **HTTP 상태코드**: `200 OK`
- **Content-Type**: `application/json`
- **응답 형태**: **스터디룸 객체 배열(JSON Array)**

### 📄 각 스터디룸 객체 구조

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| studyRoomId | integer | 스터디룸 고유 ID |
| title | string | 스터디 제목 |
| description | string | 스터디 설명 |
| organizer | string | 주최자 이름 |
| status | string | 현재 상태 (`OPEN`, `CLOSED` 등) |
| category | string | 카테고리 또는 해시태그 |
| maxMembers | integer | 최대 참여 가능 인원 수 |
| createdAtFormatted | string | 생성일시 (YYYY-MM-DD HH:mm 형태 등) |
| closedAtFormatted | string or null | 종료일시 (종료 전이면 `null`) |

### ✅ 성공 응답 예시

```json
[
  {
    "studyRoomId": 1,
    "title": "자바 백엔드 스터디",
    "description": "매주 화요일 온라인으로 진행합니다.",
    "organizer": "홍길동",
    "status": "OPEN",
    "category": "#백엔드",
    "maxMembers": 10,
    "createdAtFormatted": "2025-06-15 20:30",
    "closedAtFormatted": null
    },
  {
    "studyRoomId": 2,
    "title": "알고리즘 실전반",
    "description": "코딩 테스트 대비 집중 스터디",
    "organizer": "김철수",
    "status": "CLOSED",
    "category": "#알고리즘",
    "maxMembers": 15,
    "createdAtFormatted": "2025-05-01 10:00",
    "closedAtFormatted": "2025-06-01 18:00"
  }
]
```

### 📝 참고 사항

- 이 API는 **페이징 처리**가 없는 단순 전체 조회 기준입니다. (추후 페이지네이션 추가 가능)
- `status` 값은 백엔드 정책에 따라 `"OPEN"`, `"CLOSED"` 등 다양할 수 있습니다.
- 날짜 필드는 사용자에게 바로 보여줄 수 있도록 `createdAtFormatted` 형식으로 가공되어 전달됩니다.
- 프론트엔드에서는 이 목록을 테이블 또는 카드형 UI로 표현하여 사용자 탐색을 돕습니다.
- 
</details>


<details>
    <summary>📌 스터디룸 생성 API</summary>
    
### 📤 요청 정보

- **HTTP 메서드**: `POST`
- **URL**: `http://localhost:8080/study/create`
- **Content-Type**: `application/json`
- **인증 필요**: ✅ 로그인된 사용자 (예: JWT 토큰)
  
### 📦 요청 바디 (Request Body)

```json
{
  "title": "자바 백엔드 스터디",
  "description": "매주 온라인으로 진행하는 백엔드 학습 모임",
  "organizer": "홍길동",
  "category": "#백엔드",
  "maxMembers": 10
}
```

| 필드명 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| title | string | ✅ | 스터디 제목 |
| description | string | ✅ | 스터디 설명 |
| organizer | string | ✅ | 주최자 이름 (또는 생성자 표시용) |
| category | string | ✅ | 스터디 분류 태그 (예: `#백엔드`, `#알고리즘`) |
| maxMembers | integer | ✅ | 최대 모집 인원 (예: 10명) |

### 📥 응답 정보

- **HTTP 상태코드**: `201 Created`
- **Content-Type**: `application/json`

### 응답 바디 구조

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| studyRoomId | integer | 생성된 스터디룸의 고유 ID |
| title | string | 스터디 제목 |
| description | string | 스터디 설명 |
| organizer | string | 주최자 이름 |
| status | string | 현재 상태 (`OPEN`, `CLOSED` 등) |
| category | string | 스터디 분류 태그 |
| maxMembers | integer | 최대 모집 인원 |
| createdAtFormatted | string | 생성일시 (YYYY-MM-DD HH:mm 형식 등) |
| closedAtFormatted | string or null | 종료일시 (종료된 경우에만 값 존재, 없으면 `null`) |

### ✅ 성공 응답 예시

```json
{
  "studyRoomId": 1,
  "title": "자바 백엔드 스터디",
  "description": "매주 온라인으로 진행하는 백엔드 학습 모임",
  "organizer": "홍길동",
  "status": "OPEN",
  "category": "#백엔드",
  "maxMembers": 10,
  "createdAtFormatted": "2025-06-15 20:15",
  "closedAtFormatted": null
  }
```

### ❌ 실패 응답 예시 1 — organizer 공백

```json
{
    "error": "스터디 주최자는 필수입니다."
}
```

### ❌ 실패 응답 예시 2 — title 공백

```json
{
    "error": "스터디 제목은 필수입니다."
}
```

### ❌ 실패 응답 예시 3 — maxMembers 공백

```json
{
    "error": "최대 인원은 1명 이상이어야 합니다."
}
```

### 📝 참고 사항

- 생성된 스터디룸은 기본적으로 `OPEN` 상태로 시작되며, 모집 완료나 운영 종료 시 `CLOSED`로 변경됩니다.
- `createdAtFormatted`와 `closedAtFormatted`는 UI에 바로 출력 가능한 문자열 형태로 제공됩니다.
- `organizer`는 백엔드에서 로그인 사용자로 자동 설정될 수도 있으며, 클라이언트에서 입력받는 방식은 정책에 따라 다릅니다.
    
</details>


<details>
    <summary>📌 키워드로 스터디룸 검색 API</summary>

### 📤 요청 정보

- **HTTP 메서드**: `GET`
- **URL**: `http://localhost:8080/study/search/keyword?keyword=JPA`
- **Content-Type**: 없음 (쿼리 파라미터로 전달)
- **인증 필요**: ✅ 로그인 필요

### 🔎 쿼리 파라미터 (Query Parameters)

| 이름 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| keyword | string | ✅ | 검색 키워드. 제목, 설명, 카테고리 등을 기준으로 검색 |

예시 요청:

`GET http://localhost:8080/study/search/keyword?keyword=스프링`

### 📥 응답 정보

- **성공 시 상태코드**: `200 OK`
- **Content-Type**: `application/json`
- **응답 형식**: **스터디룸 객체 배열 (List<StudyRoom>)**


### ✅ 성공 응답 예시

```json
[
  {
    "studyRoomId": 21,
    "title": "기초부터 배우는 JPA",
    "description": "초보자를 위한 JPA 실전 학습",
    "organizer": "홍길동",
    "status": "OPEN",
    "category": "#백엔드#JPA",
    "maxMembers": 8,
    "createdAtFormatted": "2025-06-15 10:30",
    "closedAtFormatted": null},
  {
    "studyRoomId": 22,
    "title": "JPA 실무 활용",
    "description": "JPA를 프로젝트에 적용해보는 스터디",
    "organizer": "김개발",
    "status": "OPEN",
    "category": "#JPA#실무",
    "maxMembers": 12,
    "createdAtFormatted": "2025-06-01 14:00",
    "closedAtFormatted": null}
]
```

### ❌ 오류 응답 예시

```json
{
  "error": "Bad Request",
  "message": "해당 검색어로 일치하는 스터디가 없습니다.",
  "timestamp": "2025-06-16T12:30:15.8417562",
  "status": 400
}
```

### 📝 참고 사항

- 검색 키워드는 최소 1자 이상 입력되어야 하며, 미입력 시 `400 Bad Request`가 반환됩니다.
- 키워드는 스터디룸의 `title`, `description`, `category` 등에 대해 부분 일치 검색으로 적용됩니다.
- 검색 결과가 없는 경우, 빈 배열 `[]`이 반환됩니다.
- 검색 결과는 최신 생성순 또는 별도의 정렬 기준으로 반환될 수 있습니다 (정책에 따라 변경 가능).
    
</details>

<details>
    <summary>📌 댓글 등록 API</summary>

### 📤 요청 본문

- 메서드(Method): `POST`
- URL: [`http://localhost:8080/comment`](http://localhost:8080/comment)
- **헤더(Headers)**:
    - `Content-Type: application/json`
    - `Authorization: Bearer {토큰}`

### 📦 요청 바디 (Request Body)

```json
{
  "postId": 1,
  "content": "이건 test 댓글입니다."
}
```

### 📥 응답 정보

댓글이 성공적으로 등록되면, 서버는 **HTTP 200 OK** 상태와 함께 다음과 같은 정보를 담은 JSON을 반환합니다:

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| postId | integer | 댓글이 달린 게시글 ID |
| parentId | integer/null | 대댓글일 경우 부모 댓글 ID, 일반 댓글이면 `null` |
| createdUserId | string | 댓글 작성자의 사용자 ID |
| content | string | 등록한 댓글 내용 |
| createdTime | string | 댓글이 생성된 시간 (ISO 8601 형식) |
| modifiedTime | string | 댓글이 마지막으로 수정된 시간 |

### ✅ 성공 응답 예시

```json
{
  "postId": 1,
  "parentId": null,
  "createdUserId": "user07",
  "content": "이건 test 댓글입니다.",
  "createdTime": "2025-06-16T10:05:00",
  "modifiedTime": "2025-06-16T10:05:00"
}

```

### ❌ 실패 응답 예시 - 댓글 내용이 비어있을 때

```json
"댓글 내용이 비어있습니다. 댓글을 적어주세요!"

```
</details>

<details>
    <summary>📌 댓글 수정 API</summary>
    
### 📤 요청 정보

- **요청 메서드**: `PUT`
- **요청 URL**:
    
    `http://localhost:8080/comment/{commentId}`
    
    (여기서 `{commentId}`는 수정할 댓글의 고유 ID입니다)
    
- **요청 헤더**:
    - `Content-Type: application/json`
      
### 📦 요청 바디 (Request Body)

```json
{
  "postId": 1,
  "content": "수정된 댓글 내용"
}
```

| **필드명** | **타입** | **필수 여부** | **설명** |
| --- | --- | --- | --- |
| `postId` | integer | ✅ 필수 | 댓글이 달려 있는 게시글의 ID |
| `content` | string | ✅ 필수 | 새롭게 수정할 댓글 내용 |

### 📥 응답 정보

| **항목** | **타입** | **설명** |
| --- | --- | --- |
| 본문 내용 | string | 수정 결과 메시지 (예: `"댓글 수정됨\n댓글 내용: ..."` ) |


### ✅ 성공 응답 예시

```json
"댓글 수정됨"
"댓글 내용: 테스트 댓글 수정 내용"
```

### ❌ 실패 응답 예시1 - 수정할 댓글 id가 존재하지 않을 때

```json
"댓글이 존재하지 않습니다: id = 7"
```

### ❌ 실패 응답 예시2 - 수정할 내용이 빈칸일 때

```json
"수정할 댓글 내용이 비어있습니다."
```

### ❌ 실패 응답 예시3 - 수정한 내용이 전과 같을 때

```json
"수정된 내용이 없습니다. 다시 수정할 내용을 입력해주세요"
```

### 📝 비고

- `{commentId}`에 해당하는 댓글이 존재해야 합니다.
- 존재하지 않는 ID로 요청할 경우, 에러 메시지가 반환됩니다.
- 댓글 내용이 비어있거나 변경 사항이 없을 경우에도 오류 메시지를 받을 수 있습니다.
</details>


<details>
    <summary>📌 댓글 삭제 API</summary>

### 📤 요청 정보

- **메서드(Method)**: `DELETE`
- **URL**: `http://localhost:8080/comment/{id}`
- **헤더(Headers)**:
    - `Content-Type: application/json`
    - `Authorization: Bearer {토큰}`

### 📦 요청 바디 (Request Body)


### 📥 응답 정보

댓글 삭제 요청의 성공 또는 실패 여부를 나타냅니다.

| 상태 코드 | 설명 |
| --- | --- |
| `200 OK` | 댓글이 정상적으로 삭제됨 |
| `400 Bad Request` | 해당 ID의 댓글이 존재하지 않아 삭제할 수 없음 |

### ✅ 성공 응답 예시

```json
"댓글 삭제됨"
```

### ❌ 실패 응답 예시

```json
"댓글이 존재하지 않습니다: id = 5"
```

### 📝 비고

- 요청 시 `id` 경로 변수는 반드시 실제 존재하는 댓글 ID여야 합니다.
- 존재하지 않는 ID를 전달하면 400 응답과 함께 오류 메시지가 반환됩니다.
</details>


<details>
    <summary>📌 대댓글 등록 API</summary>

### 📤 요청 정보

- **요청 방식(Method)**: `POST`
- **요청 URL**: `http://localhost:8080/comment/{commentId}/reply`
    
    예시: `http://localhost:8080/comment/5/reply`
    
### 📦 요청 바디 (Request Body)

```json
{
  "postId": 1,
  "content": "테스트 대댓글입니다."
}
```

| 필드명 | 타입 | 필수 여부 | 설명 |
| --- | --- | --- | --- |
| postId | integer | ✅ 필수 | 대댓글이 달릴 게시글의 ID |
| content | string | ✅ 필수 | 대댓글의 본문 내용 |

### 📥 응답 정보 (성공 시)

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| postId | integer | 대댓글이 연결된 게시글 ID |
| parentId | integer | 부모 댓글 ID |
| createdUserId | string | 대댓글 작성자의 ID |
| content | string | 작성된 대댓글 내용 |
| createdTime | string | 생성 시각 (ISO-8601 형식) |
| modifiedTime | string | 마지막 수정 시각 (ISO-8601 형식) |

### ✅ 성공 응답 예시

```json
json
복사편집
{
  "postId": 1,
  "parentId": 5,
  "createdUserId": "user01",
  "content": "테스트 대댓글입니다.",
  "createdTime": "2025-06-16T15:21:00",
  "modifiedTime": "2025-06-16T15:21:00"
}

```
이 API는 **댓글 쓰레드 구조**를 지원하여 사용자 간의 원활한 소통을 가능하게 해줍니다.

</details>


<details>
    <summary>📌 공지사항 등록 API</summary>
    
### 📤 요청 정보

- **메서드**: `POST`
- URL : [`http://localhost:8080/notice/](http://localhost:8080/notice/1){studyRoomId}`
- **요청 형식**: JSON

### ✅ 요청 본문 필드

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| `title` | string | 공지사항 제목입니다. |
| `content` | string | 공지사항 본문(내용)입니다. |

### 📌 예시 요청

```json
json
복사편집
{
  "title": "Sample Notice Title",
  "content": "This is the content of the notice."
}

```

### 📥 응답 정보

요청이 성공하면 서버는 JSON 형식의 응답을 반환합니다.

하지만 최근 실행에서는 **서버 오류 (500)** 가 발생하였으며, 오류 응답은 다음과 같은 구조를 가집니다:

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| timestamp | string | 오류가 발생한 시간 |
| path | string | 요청이 도달한 URL 경로 |
| status | integer | HTTP 상태 코드 (`500` 등) |
| error | string | 오류 설명 |
| requestId | string | 요청을 식별하기 위한 고유 ID |

### ✅ 성공 응답 예시

```json
"공지사항 등록 완료"
"작성자: user07"
"제목: 테스트 공지사항"
"내용: 공지 내용입니다."

```
### ❌ 실패 응답 예시 1 - 공지사항 내용이 비어있을 시

```json
"비어있는 내용이 있습니다. 내용을 채워주세요."
```

### ❌ 실패 응답 예시 2 - 공지사항 권한이 없을 시

```json
"공지사항 작성 권한이 없습니다. organizerId가 3인 사용자만 작성할 수 있습니다."
```
</details>


<details>
    <summary>📌 공지사항 수정 API</summary>

### 📤 요청 정보

- **URL**: `http://localhost:8080/notice/{id}`
- **메서드**: `PUT`
- **헤더(Headers)**:
    - `Content-Type: application/json`
    - `Authorization: Bearer {토큰}`

### 📦 요청 바디 (Request Body)

```json
{
  "title": "Your Notice Title",
  "content": "The content of your notice.",
  "writerId": 1
}
```

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| `title` | string | 변경할 공지사항 제목 |
| `content` | string | 변경할 공지사항 본문 내용 |
| `writerId` | integer | 공지사항을 수정하는 작성자의 ID |

### 📥 응답 정보

- **HTTP 상태 코드**: `200 OK`
- **Content-Type**: `text/plain`
- **본문 내용**: 공지사항이 성공적으로 수정되었음을 알려주는 메시지 + 수정된 정보

### ✅ 성공 응답 예시

```json
"공지사항 수정 완료"
"작성자: user07"
"제목: 테스트 FIFA 게임"
"내용: 닉네임에 호,날,두 들어가면 다 강퇴"
```

### ❌ 실패 응답 예시 1 - 수정할 내용이 없을 시

```json
"수정된 내용이 없습니다. 다시 수정할 내용을 입력해주세요."
```

### ❌ 실패 응답 예시 2 - 수정할 공지사항이 존재하지 않을 시

```json
"공지사항이 존재하지 않습니다: id = 3"
```
</details>

<details>
    <summary>📌 공지사항 삭제 API</summary>
    
### 📤 요청 정보

- **메서드**: `DELETE`
- **URL**: `http://localhost:8080/notice/{id}`
    
    (여기서 `{id}`는 삭제하려는 공지사항의 고유 ID)
    
- **헤더(Headers)**:
    - `Content-Type: application/json`
    - `Authorization: Bearer {토큰}`

### 📦 요청 바디 (Request Body)

| 필드명 | 타입 | 필수 여부 | 설명 |
| --- | --- | --- | --- |
| `id` | integer | ✅ 필수 | 삭제할 공지사항의 고유 ID 값입니다 |

### 📥 응답 정보

- **HTTP 상태 코드**: `200 OK`
- **Content-Type**: `text/plain`

### ✅ 성공 응답 예시

```json
"공지사항 삭제 완료"
```

### ❌ 실패 응답 예시 - 공지사항이 존재하지 않을 시

```json
"공지사항이 존재하지 않습니다: id = 3"
```

</details>
