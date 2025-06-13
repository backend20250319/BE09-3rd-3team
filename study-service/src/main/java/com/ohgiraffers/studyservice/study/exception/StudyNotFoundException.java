package com.ohgiraffers.studyservice.study.exception;

public class StudyNotFoundException extends RuntimeException {
    public StudyNotFoundException(Long studyRoomId) {
        super("Study not found with id" + studyRoomId);
    }
}
