package com.ohgiraffers.studyservice.studyjoin.command.service;

import com.ohgiraffers.studyservice.study.entity.Study;
import com.ohgiraffers.studyservice.study.repository.StudyRepository;
import com.ohgiraffers.studyservice.studyjoin.command.dto.StudyJoinRequestDTO;
import com.ohgiraffers.studyservice.studyjoin.command.entity.StudyJoinEntity;
import com.ohgiraffers.studyservice.studyjoin.command.repository.StudyJoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StudyJoinService {

    private final StudyRepository studyRepository;
    private final StudyJoinRepository studyJoinRepository;

    public void joinStudy(StudyJoinRequestDTO requestDTO, String userId) {

        // 1. 스터디 존재 여부 확인
        Study study = studyRepository.findByStudyRoomId(requestDTO.getStudyRoomId())
                .orElseThrow(() -> new NoSuchElementException("스터디 ID : " + requestDTO.getStudyRoomId() + "에 해당하는 스터디를 찾을 수 없습니다."));

        // 2. 중복 신청 여부 확인
        boolean alreadyJoined = studyJoinRepository.existsByUserIdAndStudy(userId, study);
        if (alreadyJoined) {
            throw new IllegalArgumentException("이미 신청한 스터디 입니다.");
        }

        // 3. 참여 엔티티 생성 및 저장
        StudyJoinEntity entity = StudyJoinEntity.builder()
                .userId(userId)
                .study(study)
                .status(StudyJoinEntity.Status.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        studyJoinRepository.save(entity);
    }

    // 스터디 참가 신청 취소
    public String cancelJoinStudy(Long studyRoomId, String userId) {
        // 1. 스터디 확인
        Study study = studyRepository.findByStudyRoomId(studyRoomId)
                .orElseThrow(() -> new NoSuchElementException("스터디 ID : " + studyRoomId + "에 해당하는 스터디가 없습니다."));

        // 2. 신청 내역 확인
        StudyJoinEntity joinEntity = studyJoinRepository.findByUserIdAndStudy(userId, study)
                .orElseThrow(() -> new NoSuchElementException("해당 유저는 이 스터디에 신청한 내역이 없습니다."));

        // 3. 상태 확인
        if (joinEntity.getStatus() != StudyJoinEntity.Status.PENDING) {
            throw new IllegalStateException("대기 상태(PENDING)인 신청만 취소할 수 있습니다.");
        }

        // 4. 신청 취소 (삭제 또는 상태 변경)
        studyJoinRepository.delete(joinEntity);

        return "스터디 신청이 성공적으로 취소되었습니다.";
    }

}

