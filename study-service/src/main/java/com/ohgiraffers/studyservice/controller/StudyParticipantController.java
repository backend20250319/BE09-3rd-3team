package com.ohgiraffers.studyservice.controller;

import com.ohgiraffers.studyservice.dto.StudyApplyRequest;
import com.ohgiraffers.studyservice.dto.StudyParticipantSummaryResponse;
import com.ohgiraffers.studyservice.entity.StudyParticipant;
import com.ohgiraffers.studyservice.service.StudyParticipantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/study/participants")
@RequiredArgsConstructor
public class StudyParticipantController {

    private final StudyParticipantService participantService;

    // 참가 신청
    @PostMapping("/apply/{studyRoomId}")
    public ResponseEntity<Void> applyToStudy(@RequestBody StudyApplyRequest request) {
        participantService.apply(request.getStudyRoomId(), request.getUserId());
        log.info("스터디 참가 신청됨. studyRoomId={}, userId={}", request.getStudyRoomId(), request.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 대기자 목록 조회
    @GetMapping("/pending/{studyRoomId}")
    public ResponseEntity<List<StudyParticipant>> getPendingParticipants(@PathVariable Long studyRoomId) {
        List<StudyParticipant> pendingList = participantService.getPendingParticipants(studyRoomId);
        return ResponseEntity.ok(pendingList);
    }

    // 참가 승인
    @PutMapping("/approve/{participantId}")
    public ResponseEntity<Void> approveParticipant(@PathVariable Long participantId) {
        participantService.approveParticipant(participantId);
        return ResponseEntity.noContent().build();
    }

    // 승인된 참가자 목록 + 현재 인원 + 남은 자리 수 반환
    @GetMapping("/approved/{studyRoomId}")
    public ResponseEntity<StudyParticipantSummaryResponse> getApprovedParticipantsWithInfo(@PathVariable Long studyRoomId) {
        List<StudyParticipant> approvedList = participantService.getApprovedParticipants(studyRoomId);
        int approvedCount = participantService.getApprovedCount(studyRoomId);
        int maxMembers = participantService.getMaxMembers(studyRoomId);
        int remainingSeats = participantService.getRemainingSeats(studyRoomId);

        StudyParticipantSummaryResponse response = StudyParticipantSummaryResponse.builder()
                .approvedList(approvedList)
                .approvedCount(approvedCount)
                .maxMembers(maxMembers)
                .remainingSeats(remainingSeats)
                .build();

        return ResponseEntity.ok(response);
    }

    // 참가 거절
    @PutMapping("/reject/{participantId}")
    public ResponseEntity<Void> rejectParticipant(@PathVariable Long participantId) {
        participantService.rejectParticipant(participantId);
        return ResponseEntity.noContent().build();
    }
}
