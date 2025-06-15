package com.ohgiraffers.studyservice.study.repository;

import com.ohgiraffers.studyservice.study.entity.StudyStatus;
import com.ohgiraffers.studyservice.study.entity.StudyStatusRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyStatusRepository extends JpaRepository<StudyStatusRecord, Long> {

    // 단일 조회
    Optional<StudyStatusRecord> findByOrganizerId(String organizerId);
    Optional<StudyStatusRecord> findByUserId(String userId);

    // ✅ 추가: userId로 여러 건 조회
    List<StudyStatusRecord> findAllByUserId(String userId);

    // ✅ 추가: userId + 상태로 여러 건 조회
    List<StudyStatusRecord> findAllByUserIdAndStatus(String userId, StudyStatus status);
}
