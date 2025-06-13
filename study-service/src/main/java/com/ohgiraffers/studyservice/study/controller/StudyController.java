package com.ohgiraffers.studyservice.study.controller;

import com.ohgiraffers.studyservice.study.dto.StudyCreateRequest;
import com.ohgiraffers.studyservice.study.dto.StudyResponse;
import com.ohgiraffers.studyservice.study.dto.StudyUpdateRequest;
import com.ohgiraffers.studyservice.study.service.StudyService;
import com.ohgiraffers.studyservice.study.service.StudyStatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;
    private final StudyStatusService studyStatusService;

    // 스터디 개설
    @PostMapping("/create")
    public ResponseEntity<StudyResponse> createStudy(@Valid @RequestBody StudyCreateRequest request) {
        // 1) 스터디 저장
        StudyResponse response = studyService.createStudy(request);

        // 2) 스터디 상태 테이블에 추가
        studyStatusService.createStudyStatusWithId(response.getStudyRoomId());

        // 201 Created + Location 헤더
        URI location = URI.create("/study/search/" + response.getStudyRoomId());
        return ResponseEntity.created(location).body(response);
    }

    // 전체 스터디 목록 조회
    @GetMapping("/searchAll")
    public ResponseEntity<List<StudyResponse>> getAllStudies() {
        return ResponseEntity.ok(studyService.getAllStudies());
    }

    // 특정 스터디 상세 조회
    @GetMapping("/search/{studyRoomId}")
    public ResponseEntity<StudyResponse> getStudyById(@PathVariable Long studyRoomId) {
        return ResponseEntity.ok(studyService.getStudyById(studyRoomId));
    }

    // 스터디 마감 처리
    @PutMapping("/close/{studyRoomId}")
    public ResponseEntity<Void> closeStudy(@PathVariable Long studyRoomId) {
        studyService.closeStudy(studyRoomId);
        studyStatusService.closeStudyStatus(studyRoomId);
        return ResponseEntity.noContent().build();
    }

    // 스터디 수정
    @PutMapping("/update/{studyRoomId}")
    public ResponseEntity<StudyResponse> updateStudy(
            @PathVariable Long studyRoomId,
            @Valid @RequestBody StudyUpdateRequest request) {

        StudyResponse response = studyService.updateStudy(studyRoomId, request);
        return ResponseEntity.ok(response);
    }

    // 스터디 삭제
    @DeleteMapping("/delete/{studyRoomId}")
    public ResponseEntity<Void> deleteStudy(@PathVariable Long studyRoomId) {
        studyService.deleteStudy(studyRoomId);
        return ResponseEntity.noContent().build();
    }

    // 제목 또는 카테고리 키워드로 검색 (쿼리 파라미터 사용)
    @GetMapping("/search/keyword/{keyword}")
    public ResponseEntity<List<StudyResponse>> searchStudies(@PathVariable String keyword) {
        return ResponseEntity.ok(studyService.searchStudiesByKeyword(keyword));
    }
}
