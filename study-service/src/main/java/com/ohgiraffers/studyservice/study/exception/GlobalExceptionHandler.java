package com.ohgiraffers.studyservice.study.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. 유효성 검증 실패 처리 (예: @Valid, @NotNull 등)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }

    // 2. 존재하지 않는 스터디 ID 요청 처리
    @ExceptionHandler(StudyNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleStudyNotFoundException(StudyNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(404).body(error);
    }

    // 3. 커스텀 유효성 실패 처리 (필드가 비어 있거나 조건이 안 맞을 때)
    @ExceptionHandler(StudyInvalidRequestException.class)
    public ResponseEntity<Map<String, String>> handleInvalidRequestException(StudyInvalidRequestException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    // 4. 일반적인 IllegalArgumentException 처리 (예: 검색어 null 등)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
}
