package com.ohgiraffers.studyservice.study.repository;

import com.ohgiraffers.studyservice.study.entity.StudyStatusRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudyStatusRepository extends JpaRepository<StudyStatusRecord, Long> {

    // organizerId로 레코드를 조회
    Optional<StudyStatusRecord> findByOrganizerId(String organizerId);

    // userId로 레코드를 조회
    Optional<StudyStatusRecord> findByUserId(String userId);
}
