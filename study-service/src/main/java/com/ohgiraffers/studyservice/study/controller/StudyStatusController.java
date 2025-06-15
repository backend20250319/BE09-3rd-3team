package com.ohgiraffers.studyservice.study.controller;

import com.ohgiraffers.studyservice.study.dto.StudyStatusResponse;
import com.ohgiraffers.studyservice.study.entity.StudyStatusRecord;
import com.ohgiraffers.studyservice.study.service.StudyStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/study/statuses")
@RequiredArgsConstructor
public class StudyStatusController {

    private final StudyStatusService studyStatusService;

    // 새 레코드 생성 (organizerId="3", userId="2", status=OPEN)
    @PostMapping
    public ResponseEntity<StudyStatusResponse> create() {
        StudyStatusRecord record = studyStatusService.createStudyStatus();
        StudyStatusResponse dto = StudyStatusResponse.builder()
                .studyRoomId(record.getStudyRoomId())
                .organizerId(record.getOrganizerId())
                .userId(record.getUserId())
                .status(record.getStatus())
                .build();
        return ResponseEntity.ok(dto);
    }

    /** ID로 조회 */
    @GetMapping("search/id/{id}")
    public ResponseEntity<StudyStatusResponse> getById(@PathVariable Long id) {
        StudyStatusRecord record = studyStatusService.getStudyStatusById(id);
        StudyStatusResponse dto = StudyStatusResponse.builder()
                .studyRoomId(record.getStudyRoomId())
                .organizerId(record.getOrganizerId())
                .userId(record.getUserId())
                .status(record.getStatus())
                .build();
        return ResponseEntity.ok(dto);
    }

    /** organizerId로 조회 */
    @GetMapping("search/user/{organizerId}")
    public ResponseEntity<StudyStatusResponse> getByUser(@PathVariable String organizerId) {
        StudyStatusRecord record = studyStatusService.getByUserId(organizerId);
        StudyStatusResponse dto = StudyStatusResponse.builder()
                .studyRoomId(record.getStudyRoomId())
                .organizerId(record.getOrganizerId())
                .userId(record.getUserId())
                .status(record.getStatus())
                .build();
        return ResponseEntity.ok(dto);
    }
}
