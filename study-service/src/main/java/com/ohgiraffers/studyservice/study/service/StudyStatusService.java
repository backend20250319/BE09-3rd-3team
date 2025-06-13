package com.ohgiraffers.studyservice.study.service;

import com.ohgiraffers.studyservice.study.entity.StudyStatusRecord;
import com.ohgiraffers.studyservice.study.entity.StudyStatus;
import com.ohgiraffers.studyservice.study.exception.StudyStatusNotFoundException;
import com.ohgiraffers.studyservice.study.repository.StudyStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 스터디 상태 관리 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional
public class StudyStatusService {

    private final StudyStatusRepository studyStatusRepository;

    /**
     * 새로운 스터디 상태 레코드를 생성합니다.
     * userId는 고정값 "2", status는 OPEN으로 초기화됩니다.
     *
     * @return 생성된 StudyStatusRecord
     */
    public StudyStatusRecord createStudyStatus() {
        StudyStatusRecord record = StudyStatusRecord.builder()
                .build(); // @PrePersist에서 userId 및 status가 설정됨
        return studyStatusRepository.save(record);
    }

    /**
     * 전달된 ID는 무시하고, JPA IDENTITY 전략으로 자동 생성된 PK를 사용하여
     * 새로운 스터디 상태 레코드를 생성합니다.
     *
     * @param ignored 무시되는 파라미터
     * @return 생성된 StudyStatusRecord
     */
    public StudyStatusRecord createStudyStatusWithId(Long ignored) {
        StudyStatusRecord record = StudyStatusRecord.builder()
                .build(); // @PrePersist에서 userId 및 status 설정
        return studyStatusRepository.save(record);
    }

    /**
     * 주어진 ID에 해당하는 스터디 상태 레코드를 조회합니다.
     *
     * @param id 스터디 상태 레코드 ID
     * @return 조회된 StudyStatusRecord
     */
    @Transactional(readOnly = true)
    public StudyStatusRecord getStudyStatusById(Long id) {
        return studyStatusRepository.findById(id)
                .orElseThrow(() -> new StudyStatusNotFoundException(
                        "스터디 상태 레코드를 찾을 수 없습니다. id=" + id));
    }

    /**
     * 주어진 userId에 해당하는 스터디 상태 레코드를 조회합니다.
     *
     * @param userId 스터디 개설자 ID
     * @return 조회된 StudyStatusRecord
     */
    @Transactional(readOnly = true)
    public StudyStatusRecord getByUserId(String userId) {
        return studyStatusRepository.findByUserId(userId)
                .orElseThrow(() -> new StudyStatusNotFoundException(
                        "userId='" + userId + "'인 레코드를 찾을 수 없습니다."));
    }

    /**
     * 스터디 상태를 CLOSED로 업데이트합니다.
     *
     * @param id 스터디 상태 레코드 ID
     * @return 업데이트된 StudyStatusRecord
     */
    public StudyStatusRecord closeStudyStatus(Long id) {
        StudyStatusRecord record = getStudyStatusById(id);
        record.setStatus(StudyStatus.CLOSED);
        return studyStatusRepository.save(record);
    }
}
