package com.ohgiraffers.studyservice.study.repasitory;

import com.ohgiraffers.studyservice.study.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyRepository extends JpaRepository<Study, Long> {
    List<Study> findByStatus(String status);
    List<Study> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(String title, String category);
}
