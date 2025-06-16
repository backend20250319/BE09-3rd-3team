package com.ohgiraffers.studyservice.study.service;

import com.ohgiraffers.studyservice.study.dto.StudyStatusResponse;
import com.ohgiraffers.studyservice.study.entity.StudyStatus;
import com.ohgiraffers.studyservice.study.entity.StudyStatusRecord;
import com.ohgiraffers.studyservice.study.exception.StudyStatusNotFoundException;
import com.ohgiraffers.studyservice.study.repository.StudyStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class StudyStatusService {

    private final StudyStatusRepository studyStatusRepository;

    public StudyStatusRecord createStudyStatus() {
        StudyStatusRecord record = StudyStatusRecord.builder()
                .status(StudyStatus.OPEN)
                .build();
        StudyStatusRecord saved = studyStatusRepository.save(record);
        log.info("스터디 상태 레코드 생성됨: {}", saved);
        return saved;
    }

    public StudyStatusRecord createStudyStatusWithUserId(String userId) {
        StudyStatusRecord record = StudyStatusRecord.builder()
                .userId(userId)
                .status(StudyStatus.OPEN)
                .build();
        StudyStatusRecord saved = studyStatusRepository.save(record);
        log.info("userId='{}'로 스터디 상태 생성됨", userId);
        return saved;
    }

    @Transactional(readOnly = true)
    public StudyStatusRecord getStudyStatusById(Long id) {
        return studyStatusRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("스터디 상태 조회 실패: id={} 존재하지 않음", id);
                    return new StudyStatusNotFoundException("스터디 상태 레코드를 찾을 수 없습니다. id=" + id);
                });
    }

    @Transactional(readOnly = true)
    public StudyStatusRecord getByUserId(String organizerId) {
        if (!organizerId.startsWith("user")) {
            log.warn("유효하지 않은 userId 형식: {}", organizerId);
            throw StudyStatusNotFoundException.invalidFormat(organizerId);
        }

        return studyStatusRepository.findByOrganizerId(organizerId)
                .orElseThrow(() -> {
                    log.warn("스터디 상태 조회 실패: userId='{}' 존재하지 않음", organizerId);
                    return new StudyStatusNotFoundException(organizerId);
                });
    }

    public StudyStatusRecord closeStudyStatus(Long id) {
        StudyStatusRecord record = getStudyStatusById(id);
        record.setStatus(StudyStatus.CLOSED);
        StudyStatusRecord saved = studyStatusRepository.save(record);
        log.info("스터디 상태 마감 처리 완료 [id={}, userId={}, status=CLOSED]", id, record.getUserId());
        return saved;
    }

    @Transactional(readOnly = true)
    public List<StudyStatusRecord> getAllByUserId(String userId) {
        List<StudyStatusRecord> list = studyStatusRepository.findAllByUserId(userId);
        log.info("userId='{}'의 전체 스터디 상태 {}건 조회됨", userId, list.size());
        return list;
    }

    @Transactional(readOnly = true)
    public List<StudyStatusRecord> getAllOpenByUserId(String userId) {
        List<StudyStatusRecord> list = studyStatusRepository.findAllByUserIdAndStatus(userId, StudyStatus.OPEN);
        log.info("userId='{}'의 OPEN 상태 스터디 {}건 조회됨", userId, list.size());
        return list;
    }

    @Transactional(readOnly = true)
    public List<StudyStatusRecord> getAllClosedByUserId(String userId) {
        List<StudyStatusRecord> list = studyStatusRepository.findAllByUserIdAndStatus(userId, StudyStatus.CLOSED);
        log.info("userId='{}'의 CLOSED 상태 스터디 {}건 조회됨", userId, list.size());
        return list;
    }

    @Transactional(readOnly = true)
    public StudyStatusResponse getStudyInfo(Long studyRoomId) {
        StudyStatusRecord record = getStudyStatusById(studyRoomId);
        StudyStatusResponse response = new StudyStatusResponse();
        response.setStudyRoomId(record.getStudyRoomId());
        response.setOrganizerId(String.valueOf(record.getOrganizerId()));
        response.setUserId(record.getUserId());
        response.setStatus(record.getStatus());
        log.info("스터디 정보 반환됨: studyRoomId={}, userId={}, status={}", record.getStudyRoomId(), record.getUserId(), record.getStatus());
        return response;
    }
}
