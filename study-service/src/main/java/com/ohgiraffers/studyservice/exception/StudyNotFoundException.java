package com.ohgiraffers.studyservice.exception;

public class StudyNotFoundException extends RuntimeException {
    public StudyNotFoundException(Long studyRoomId) {
        super("Study not found with id" + studyRoomId);
    }
}
