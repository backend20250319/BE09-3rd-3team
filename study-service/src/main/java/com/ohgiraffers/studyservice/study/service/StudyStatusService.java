package com.ohgiraffers.studyservice.study.service;

import com.ohgiraffers.studyservice.study.entity.StudyStatusRecord;
import com.ohgiraffers.studyservice.study.entity.StudyStatus;
import com.ohgiraffers.studyservice.study.exception.StudyStatusNotFoundException;
import com.ohgiraffers.studyservice.study.repository.StudyStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


// 스터디 상태 관리 서비스

@Service
@RequiredArgsConstructor
@Transactional
public class StudyStatusService {

    private final StudyStatusRepository studyStatusRepository;

// userId는 고정값 "2", status는 OPEN으로 초기화
    public StudyStatusRecord createStudyStatus() {
        StudyStatusRecord record = StudyStatusRecord.builder()
                .build(); // @PrePersist에서 userId 및 status가 설정됨
        return studyStatusRepository.save(record);
    }


//  @param ignored 무시되는 파라미터
    public StudyStatusRecord createStudyStatusWithId(Long ignored) {
        StudyStatusRecord record = StudyStatusRecord.builder()
                .build(); // @PrePersist에서 userId 및 status 설정
        return studyStatusRepository.save(record);
    }



//     * @param id 스터디 상태 레코드 ID
    @Transactional(readOnly = true)
    public StudyStatusRecord getStudyStatusById(Long id) {
        return studyStatusRepository.findById(id)
                .orElseThrow(() -> new StudyStatusNotFoundException(
                        "스터디 상태 레코드를 찾을 수 없습니다. id=" + id));
    }


//     * 주어진 userId에 해당하는 스터디 상태 레코드를 조회
//     * @param userId 스터디 개설자 ID
    @Transactional(readOnly = true)
    public StudyStatusRecord getByUserId(String userId) {
        return studyStatusRepository.findByUserId(userId)
                .orElseThrow(() -> new StudyStatusNotFoundException(
                        "userId='" + userId + "'인 레코드를 찾을 수 없습니다."));
    }


//     * 스터디 상태를 CLOSED로 업데이트.
//     * @param id 스터디 상태 레코드 ID
    public StudyStatusRecord closeStudyStatus(Long id) {
        StudyStatusRecord record = getStudyStatusById(id);
        record.setStatus(StudyStatus.CLOSED);
        return studyStatusRepository.save(record);
    }
}
