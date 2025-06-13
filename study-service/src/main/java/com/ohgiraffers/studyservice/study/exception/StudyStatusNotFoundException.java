package com.ohgiraffers.studyservice.study.exception;

// 요청한 스터디 상태 레코드(ID, userId 등)를 찾을 수 없을 때 던집니다.

public class StudyStatusNotFoundException extends RuntimeException {

    // ID 기반 조회 실패
    public StudyStatusNotFoundException(Long studyRoomId) {
        super("스터디 상태 레코드를 찾을 수 없습니다. id=" + studyRoomId);
    }

    // userId 기반 조회 실패
    public StudyStatusNotFoundException(String userId) {
        super("userId='" + userId + "'인 스터디 상태 레코드를 찾을 수 없습니다.");
    }

    // 커스텀 메시지 생성자
    public StudyStatusNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
