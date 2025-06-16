package com.ohgiraffers.studyservice.study.exception;

/**
 * 요청한 스터디 상태 레코드(ID, userId 등)를 찾을 수 없을 때 던집니다.
 */
public class StudyStatusNotFoundException extends RuntimeException {

    // ID 기반 조회 실패
    public StudyStatusNotFoundException(Long studyRoomId) {
        super("스터디 상태 레코드를 찾을 수 없습니다. id=" + studyRoomId);
    }

    // userId 기반 조회 실패 (형식은 맞음, 존재하지 않음)
    public StudyStatusNotFoundException(String userId) {
        super("해당 userId의 스터디 상태 정보가 존재하지 않습니다: " + userId);
    }

    // userId 형식이 잘못되었을 때
    public static StudyStatusNotFoundException invalidFormat(String userId) {
        return new StudyStatusNotFoundException("userId 형식이 잘못되었습니다. 'userXXX' 형식으로 입력해주세요: " + userId);
    }

    // 커스텀 메시지와 원인 포함
    public StudyStatusNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
