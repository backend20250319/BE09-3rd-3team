package com.ohgiraffers.studyservice.study.exception;

public class StudyInvalidRequestException extends RuntimeException {
    public StudyInvalidRequestException(String message) {
        super(message);
    }
}