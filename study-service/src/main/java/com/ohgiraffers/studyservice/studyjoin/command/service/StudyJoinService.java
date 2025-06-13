package com.ohgiraffers.studyservice.studyjoin.command.service;

import com.ohgiraffers.studyservice.studyjoin.command.dto.StudyJoinRequestDTO;
import com.ohgiraffers.studyservice.studyjoin.command.entity.StudyJoinEntity;
import com.ohgiraffers.studyservice.studyjoin.command.repository.StudyJoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StudyJoinService {

    private final StudyJoinRepository studyJoinRepository;

    public void joinStudy(StudyJoinRequestDTO dto, Long userId) {
        StudyJoinEntity entity = StudyJoinEntity.of(dto, userId, LocalDateTime.now());
        studyJoinRepository.save(entity);
    }

}
