package com.ohgiraffers.studyservice.studyjoin.command.repository;

import com.ohgiraffers.studyservice.study.entity.Study;
import com.ohgiraffers.studyservice.studyjoin.command.entity.StudyJoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyJoinRepository extends JpaRepository<StudyJoinEntity, Long> {
    boolean existsByUserIdAndStudy(String userId, Study study);

    List<StudyJoinEntity> findByUserId(String userId);

    Optional<StudyJoinEntity> findByUserIdAndStudy(String userId, Study study);
}

