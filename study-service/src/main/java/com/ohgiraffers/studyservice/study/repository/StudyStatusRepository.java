package com.ohgiraffers.studyservice.study.repository;

import com.ohgiraffers.studyservice.study.entity.StudyStatusRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudyStatusRepository extends JpaRepository<StudyStatusRecord, Long> {

    // organizeId로 레코드를 조회하기 위한 메서드
    Optional<StudyStatusRecord> findByOrganizerId(String organizerId);
}
