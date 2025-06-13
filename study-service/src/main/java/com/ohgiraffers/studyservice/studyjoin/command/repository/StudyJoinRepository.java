package com.ohgiraffers.studyservice.studyjoin.command.repository;

import com.ohgiraffers.studyservice.studyjoin.command.entity.StudyJoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyJoinRepository extends JpaRepository<StudyJoinEntity, Long> {
}

