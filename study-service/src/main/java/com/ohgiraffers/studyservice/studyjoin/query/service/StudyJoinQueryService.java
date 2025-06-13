package com.ohgiraffers.studyservice.studyjoin.query.service;

import com.ohgiraffers.studyservice.studyjoin.query.dto.StudyJoinListDTO;
import com.ohgiraffers.studyservice.studyjoin.query.repository.StudyJoinListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyJoinQueryService {

    private final StudyJoinListRepository studyJoinListRepository;

    public List<StudyJoinListDTO> findByUserId(Long userId) {
        return studyJoinListRepository.findByUserId(userId).stream()
                .map(StudyJoinListDTO::fromEntity)
                .collect(Collectors.toList());
    }
}

