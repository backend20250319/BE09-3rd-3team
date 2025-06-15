package com.ohgiraffers.studyservice.studyjoin.command.repository;

import com.ohgiraffers.studyservice.study.entity.Study;
import com.ohgiraffers.studyservice.studyjoin.command.entity.StudyJoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyJoinRepository extends JpaRepository<StudyJoinEntity, Long> {
    boolean existsByUserIdAndStudy(String userId, Study study);

    List<StudyJoinEntity> findByUserId(String userId);
}

