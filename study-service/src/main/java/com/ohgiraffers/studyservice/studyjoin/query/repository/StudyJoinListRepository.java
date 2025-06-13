package com.ohgiraffers.studyservice.studyjoin.query.repository;

import com.ohgiraffers.studyservice.studyjoin.query.entity.StudyJoinListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyJoinListRepository extends JpaRepository<StudyJoinListEntity, Long> {
    List<StudyJoinListEntity> findByUserId(Long userId);
}
