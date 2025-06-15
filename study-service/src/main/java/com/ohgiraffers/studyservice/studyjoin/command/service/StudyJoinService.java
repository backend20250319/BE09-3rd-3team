package com.ohgiraffers.studyservice.studyjoin.command.service;

import com.ohgiraffers.studyservice.studyjoin.command.dto.StudyJoinRequestDTO;
import com.ohgiraffers.studyservice.studyjoin.command.entity.StudyJoinEntity;
import com.ohgiraffers.studyservice.studyjoin.command.repository.StudyJoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StudyJoinService {

    private final StudyJoinRepository studyJoinRepository;

    public void joinStudy(StudyJoinRequestDTO dto, String userId) {
        StudyJoinEntity entity = StudyJoinEntity.of(dto, userId, LocalDateTime.now());

        try {
            studyJoinRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("이미 신청한 스터디 입니다.");
        }
    }
}
