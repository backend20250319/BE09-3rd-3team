package com.ohgiraffers.studyservice.study.repository;

import com.ohgiraffers.studyservice.study.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyRepository extends JpaRepository<Study, Long> {
    List<Study> findByStatus(String status);
    List<Study> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(String title, String category);

    Optional<Study> findByStudyRoomId(Long studyRoomId);
}
