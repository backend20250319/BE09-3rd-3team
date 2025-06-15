package com.ohgiraffers.studyservice.studyjoin.query.service;

import com.ohgiraffers.studyservice.studyjoin.command.entity.StudyJoinEntity;
import com.ohgiraffers.studyservice.studyjoin.command.repository.StudyJoinRepository;
import com.ohgiraffers.studyservice.studyjoin.query.dto.StudyJoinListDTO;
import com.ohgiraffers.studyservice.studyjoin.query.repository.StudyJoinListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyJoinQueryService {

    private final StudyJoinRepository studyJoinRepository;

    public List<StudyJoinListDTO> getMyStudyJoins(String userId) {
        return studyJoinRepository.findByUserId(userId).stream()
                .map(StudyJoinListDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
