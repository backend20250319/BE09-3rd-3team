# 💡 3팀 스터디 모집 플랫폼

스터디를 개설하고, 참가하고, 승인/거절하며  
관리할 수 있는 **스터디 모집 통합 서비스**입니다.

### [Backend Source](https://github.com/backend20250319/BE09-3rd-3team)
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


## 🚀 기술 스택

![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring Boot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![AWS](https://img.shields.io/badge/AWS-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)


---

### 마이크로서비스 구조
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
## 3. 인터페이스 설계서
### 3-1. API 설계

