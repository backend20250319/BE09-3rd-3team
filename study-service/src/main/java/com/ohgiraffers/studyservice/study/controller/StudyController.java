package com.ohgiraffers.studyservice.study.controller;

import com.ohgiraffers.studyservice.study.dto.StudyCreateRequest;
import com.ohgiraffers.studyservice.study.dto.StudyResponse;
import com.ohgiraffers.studyservice.study.dto.StudyUpdateRequest;
import com.ohgiraffers.studyservice.study.service.StudyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;

    //  스터디 개설
    @PostMapping("/create")
    public ResponseEntity<StudyResponse> createStudy(@Valid @RequestBody StudyCreateRequest request) {
        StudyResponse response = studyService.createStudy(request);
        return ResponseEntity.ok(response);
    }

    //  전체 스터디 목록 조회
    @GetMapping
    public ResponseEntity<List<StudyResponse>> getAllStudies() {
        return ResponseEntity.ok(studyService.getAllStudies());
    }

    //  스터디 상세 조회
    @GetMapping("/{studyRoomId}")
    public ResponseEntity<StudyResponse> getStudyById(@PathVariable Long studyRoomId) {
        return ResponseEntity.ok(studyService.getStudyById(studyRoomId));
    }

    //  스터디 마감 처리 (마감일시 설정은 서비스에서 수행)
    @PutMapping("/close/{studyRoomId}")
    public ResponseEntity<Void> closeStudy(@PathVariable Long studyRoomId) {
        studyService.closeStudy(studyRoomId);
        return ResponseEntity.noContent().build();
    }

    //  스터디 수정
    @PutMapping("/update/{studyRoomId}")
    public ResponseEntity<StudyResponse> updateStudy(@PathVariable Long studyRoomId,
                                                     @Valid @RequestBody StudyUpdateRequest request) {
        StudyResponse response = studyService.updateStudy(studyRoomId, request);
        return ResponseEntity.ok(response);
    }

    //  스터디 삭제
    @DeleteMapping("/delete/{studyRoomId}")
    public ResponseEntity<Void> deleteStudy(@PathVariable Long studyRoomId) {
        studyService.deleteStudy(studyRoomId);
        return ResponseEntity.noContent().build();
    }

    //  제목 또는 카테고리 키워드로 검색
    @GetMapping("/search/title/{keyword}")
    public ResponseEntity<List<StudyResponse>> searchStudies(@PathVariable String keyword) {
        return ResponseEntity.ok(studyService.searchStudiesByKeyword(keyword));
    }
}
