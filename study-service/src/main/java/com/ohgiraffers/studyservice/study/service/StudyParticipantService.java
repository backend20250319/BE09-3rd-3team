//package com.ohgiraffers.studyservice.study.service;
//
//import com.ohgiraffers.studyservice.study.entity.Study;
//import com.ohgiraffers.studyservice.study.entity.StudyParticipant;
//import com.ohgiraffers.studyservice.study.entity.ParticipationStatus;
//import com.ohgiraffers.studyservice.study.exception.ParticipantNotFoundException;
//import com.ohgiraffers.studyservice.study.exception.StudyStatusNotFoundException;
//import com.ohgiraffers.studyservice.studyjoin.command.entity.StudyJoinEntity;
//import com.ohgiraffers.studyservice.studyjoin.command.repository.StudyJoinRepository;
//import com.ohgiraffers.studyservice.study.repository.StudyParticipantRepository;
//import com.ohgiraffers.studyservice.study.repository.StudyRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//// 스터디 참가(신청/승인/거절) 관리 서비스
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class StudyParticipantService {
//
//    private final StudyJoinRepository studyJoinRepository;
//    private final StudyRepository studyRepository;
//    private final StudyParticipantRepository studyParticipantRepository;
//
////     스터디 참가 신청
//    public void apply(Long studyRoomId, Long userId) {
//        Study study = studyRepository.findById(studyRoomId)
//                .orElseThrow(() -> new StudyStatusNotFoundException(studyRoomId));
//
//        StudyJoinEntity join = new StudyJoinEntity();
//        join.setStudyId(studyRoomId);
//        join.setUserId(userId);
//        join.setStatus(StudyJoinEntity.Status.PENDING);
//        join.setCreatedAt(LocalDateTime.now());
//
//        studyJoinRepository.save(join);
//        log.info("스터디 참가 신청 완료. [joinId={}, studyId={}, userId={}]", join.getId(), studyRoomId, userId);
//    }
//
////  대기 중인 참가자 목록 조회
//    @Transactional(readOnly = true)
//    public List<StudyJoinEntity> getPendingApplications(Long studyRoomId) {
//        return studyJoinRepository.findAll().stream()
//                .filter(join -> join.getStudyId().equals(studyRoomId)
//                        && join.getStatus() == StudyJoinEntity.Status.PENDING)
//                .collect(Collectors.toList());
//    }
//
////  참가 승인
//    public void approveParticipant(Long joinId) {
//        StudyJoinEntity join = studyJoinRepository.findById(joinId)
//                .orElseThrow(() -> new ParticipantNotFoundException(joinId));
//        join.setStatus(StudyJoinEntity.Status.APPROVED);
//        studyJoinRepository.save(join);
//
//        Study study = studyRepository.findById(join.getStudyId())
//                .orElseThrow(() -> new StudyStatusNotFoundException(join.getStudyId()));
//        StudyParticipant participant = StudyParticipant.builder()
//                .study(study)
//                .userId(join.getUserId())
//                .status(ParticipationStatus.APPROVED)
//                .build();
//        studyParticipantRepository.save(participant);
//        log.info("참가 승인 완료. [joinId={}, studyId={}, userId={}]", joinId, join.getStudyId(), join.getUserId());
//    }
//
////  참가 거절
//    public void rejectParticipant(Long joinId) {
//        StudyJoinEntity join = studyJoinRepository.findById(joinId)
//                .orElseThrow(() -> new ParticipantNotFoundException(joinId));
//        join.setStatus(StudyJoinEntity.Status.REJECTED);
//        studyJoinRepository.save(join);
//        log.info("참가 거절 완료. [joinId={}, studyId={}, userId={}]", joinId, join.getStudyId(), join.getUserId());
//    }
//
////   승인된 참가자 목록 조회
//    @Transactional(readOnly = true)
//    public List<StudyParticipant> getApprovedParticipants(Long studyRoomId) {
//        return studyParticipantRepository.findByStudy_StudyRoomIdAndStatus(
//                studyRoomId, ParticipationStatus.APPROVED);
//    }
//
////  승인된 참가자 수 조회
//    @Transactional(readOnly = true)
//    public int getApprovedCount(Long studyRoomId) {
//        return getApprovedParticipants(studyRoomId).size();
//    }
//
////   스터디 최대 인원 조회
//    @Transactional(readOnly = true)
//    public int getMaxMembers(Long studyRoomId) {
//        Study study = studyRepository.findById(studyRoomId)
//                .orElseThrow(() -> new StudyStatusNotFoundException(studyRoomId));
//        return study.getMaxMembers();
//    }
//
//
////     * 남은 자리 수 계산
//    @Transactional(readOnly = true)
//    public int getRemainingSeats(Long studyRoomId) {
//        return Math.max(getMaxMembers(studyRoomId) - getApprovedCount(studyRoomId), 0);
//    }
//}
