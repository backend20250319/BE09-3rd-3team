//package com.ohgiraffers.studyservice.study.controller;
//
//import com.ohgiraffers.studyservice.study.dto.StudyParticipantSummaryResponse;
//import com.ohgiraffers.studyservice.study.entity.StudyParticipant;
//import com.ohgiraffers.studyservice.studyjoin.command.entity.StudyJoinEntity;
//import com.ohgiraffers.studyservice.study.service.StudyParticipantService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Slf4j
//@RestController
//@RequestMapping("/study/participants")
//@RequiredArgsConstructor
//public class StudyParticipantController {
//
//    private final StudyParticipantService participantService;
//
////    대기 중인 스터디 신청 목록 조회
//    @GetMapping("/pending/{studyRoomId}")
//    public ResponseEntity<List<StudyJoinEntity>> getPendingApplications(@PathVariable Long studyRoomId) {
//        List<StudyJoinEntity> pendingList = participantService.getPendingApplications(studyRoomId);
//        return ResponseEntity.ok(pendingList);
//    }
//
////     스터디 참가 승인
//    @PutMapping("/approve/{joinId}")
//    public ResponseEntity<Void> approveParticipant(@PathVariable Long joinId) {
//        participantService.approveParticipant(joinId);
//        return ResponseEntity.noContent().build();
//    }
//
////   승인된 참가자 목록 + 현재 인원 + 남은 자리 수 반환
//
//    @GetMapping("/approved/{studyRoomId}")
//    public ResponseEntity<StudyParticipantSummaryResponse> getApprovedParticipantsWithInfo(
//            @PathVariable Long studyRoomId) {
//        List<StudyParticipant> approvedList = participantService.getApprovedParticipants(studyRoomId);
//        int approvedCount = participantService.getApprovedCount(studyRoomId);
//        int maxMembers = participantService.getMaxMembers(studyRoomId);
//        int remainingSeats = participantService.getRemainingSeats(studyRoomId);
//
//        StudyParticipantSummaryResponse response = StudyParticipantSummaryResponse.builder()
//                .approvedList(approvedList)
//                .approvedCount(approvedCount)
//                .maxMembers(maxMembers)
//                .remainingSeats(remainingSeats)
//                .build();
//
//        return ResponseEntity.ok(response);
//    }
//
////    스터디 참가 거절
//
//    @PutMapping("/reject/{joinId}")
//    public ResponseEntity<Void> rejectParticipant(@PathVariable Long joinId) {
//        participantService.rejectParticipant(joinId);
//        return ResponseEntity.noContent().build();
//    }
//}
