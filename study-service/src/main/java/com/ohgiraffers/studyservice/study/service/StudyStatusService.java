package com.ohgiraffers.studyservice.study.service;

import com.ohgiraffers.studyservice.study.dto.StudyStatusResponse;
import com.ohgiraffers.studyservice.study.entity.StudyStatus;
import com.ohgiraffers.studyservice.study.entity.StudyStatusRecord;
import com.ohgiraffers.studyservice.study.exception.StudyStatusNotFoundException;
import com.ohgiraffers.studyservice.study.repository.StudyStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyStatusService {

    private final StudyStatusRepository studyStatusRepository;

    /**
     * [현재 미사용] userId 없이 스터디 상태 생성 (테스트 용도 등)
     */
    public StudyStatusRecord createStudyStatus() {
        StudyStatusRecord record = StudyStatusRecord.builder()
                .status(StudyStatus.OPEN)
                .build();
        return studyStatusRepository.save(record);
    }

    /**
     * 주어진 userId로 StudyStatusRecord 저장
     */
    public StudyStatusRecord createStudyStatusWithUserId(String userId) {
        StudyStatusRecord record = StudyStatusRecord.builder()
                .userId(userId)
                .status(StudyStatus.OPEN)
                .build();
        return studyStatusRepository.save(record);
    }

    /**
     * studyRoomId로 스터디 상태 조회
     */
    @Transactional(readOnly = true)
    public StudyStatusRecord getStudyStatusById(Long id) {
        return studyStatusRepository.findById(id)
                .orElseThrow(() -> new StudyStatusNotFoundException("스터디 상태 레코드를 찾을 수 없습니다. id=" + id));
    }

    /**
     * organizerId로 스터디 상태 조회
     */
    @Transactional(readOnly = true)
    public StudyStatusRecord getByUserId(String organizerId) {
        return studyStatusRepository.findByOrganizerId(organizerId)
                .orElseThrow(() -> new StudyStatusNotFoundException("userId='" + organizerId + "'인 레코드를 찾을 수 없습니다."));
    }

    /**
     * 스터디 상태를 CLOSED로 변경
     */
    public StudyStatusRecord closeStudyStatus(Long id) {
        StudyStatusRecord record = getStudyStatusById(id);
        record.setStatus(StudyStatus.CLOSED);
        return studyStatusRepository.save(record);
    }

    // ✅ 추가: 특정 userId가 가진 모든 스터디 상태 레코드 조회
    @Transactional(readOnly = true)
    public List<StudyStatusRecord> getAllByUserId(String userId) {
        return studyStatusRepository.findAllByUserId(userId);
    }

    // ✅ 추가: 특정 userId가 가진 OPEN 상태만 조회
    @Transactional(readOnly = true)
    public List<StudyStatusRecord> getAllOpenByUserId(String userId) {
        return studyStatusRepository.findAllByUserIdAndStatus(userId, StudyStatus.OPEN);
    }

    // ✅ 추가: 특정 userId가 가진 CLOSED 상태만 조회
    @Transactional(readOnly = true)
    public List<StudyStatusRecord> getAllClosedByUserId(String userId) {
        return studyStatusRepository.findAllByUserIdAndStatus(userId, StudyStatus.CLOSED);
    }

    // ✅ 추가: studyRoomId로 organizerId + userId 반환용 응답 DTO 제공
    @Transactional(readOnly = true)
    public StudyStatusResponse getStudyInfo(Long studyRoomId) {
        StudyStatusRecord record = getStudyStatusById(studyRoomId);
        StudyStatusResponse response = new StudyStatusResponse();
        response.setStudyRoomId(record.getStudyRoomId());
        response.setOrganizerId(String.valueOf(record.getOrganizerId()));
        response.setUserId(record.getUserId());
        response.setStatus(record.getStatus());

        return response;
    }
}