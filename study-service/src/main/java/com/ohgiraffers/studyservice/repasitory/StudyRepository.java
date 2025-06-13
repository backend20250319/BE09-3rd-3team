package com.ohgiraffers.studyservice.repasitory;

import com.ohgiraffers.studyservice.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyRepository extends JpaRepository<Study, Long> {
    List<Study> findByClosedFalse();
    List<Study> findByStatus(String status);
    List<Study> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(String title, String category);
}
