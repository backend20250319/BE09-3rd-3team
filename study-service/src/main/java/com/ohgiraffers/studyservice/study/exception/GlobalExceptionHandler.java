package com.ohgiraffers.studyservice.study.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. 유효성 검증 실패 처리 (@Valid 등)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        log.warn("유효성 검사 실패: {}", errors);

        response.put("timestamp", LocalDateTime.now());
        response.put("status", 400);
        response.put("error", "Validation Failed");
        response.put("messages", errors);

        return ResponseEntity.badRequest().body(response);
    }

    // 2. 스터디 상태 레코드를 찾을 수 없음
    @ExceptionHandler(StudyStatusNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleStudyStatusNotFoundException(StudyStatusNotFoundException ex) {
        log.warn("스터디 상태 레코드 조회 실패: {}", ex.getMessage());

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", 404);
        response.put("error", "Study Status Not Found");
        response.put("message", ex.getMessage());

        return ResponseEntity.status(404).body(response);
    }

    // 3. 커스텀 유효성 실패 처리 (필드 누락 등)
    @ExceptionHandler(StudyInvalidRequestException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidRequestException(StudyInvalidRequestException ex) {
        log.warn("잘못된 요청: {}", ex.getMessage());

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", 400);
        response.put("error", "Invalid Study Request");
        response.put("message", ex.getMessage());

        return ResponseEntity.badRequest().body(response);
    }

    // 4. 일반적인 IllegalArgumentException 처리 (예: 검색어 누락 등)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("IllegalArgumentException 발생: {}", ex.getMessage());

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", 400);
        response.put("error", "Bad Request");
        response.put("message", ex.getMessage());

        return ResponseEntity.badRequest().body(response);
    }

    // 5. 예기치 않은 모든 오류 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleOtherExceptions(Exception ex) {
        log.error("알 수 없는 서버 오류 발생", ex);

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", 500);
        response.put("error", "Internal Server Error");
        response.put("message", ex.getMessage());

        return ResponseEntity.status(500).body(response);
    }
}
