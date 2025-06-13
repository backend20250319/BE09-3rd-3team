package com.ohgiraffers.studyservice.study.service;

import com.ohgiraffers.studyservice.study.entity.ParticipationStatus;
import com.ohgiraffers.studyservice.study.entity.Study;
import com.ohgiraffers.studyservice.study.entity.StudyParticipant;
import com.ohgiraffers.studyservice.study.exception.ParticipantNotFoundException;
import com.ohgiraffers.studyservice.study.exception.StudyNotFoundException;
import com.ohgiraffers.studyservice.study.repasitory.StudyParticipantRepository;
import com.ohgiraffers.studyservice.study.repasitory.StudyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudyParticipantService {

    private final StudyParticipantRepository studyParticipantRepository;
    private final StudyRepository studyRepository;

    // 참가 신청

    public void apply(Long studyRoomId, Long userId) {
        Study study = studyRepository.findById(studyRoomId)
                .orElseThrow(() -> new StudyNotFoundException(studyRoomId));

        StudyParticipant participant = StudyParticipant.builder()
                .study(study)
                .userId(userId)
                .status(ParticipationStatus.PENDING)
                .build();

        studyParticipantRepository.save(participant);
        log.info("스터디 참가 신청 완료. [studyRoomId={}, userId={}]", studyRoomId, userId);
    }

  //  대기자 목록 조회
    public List<StudyParticipant> getPendingParticipants(Long studyRoomId) {
        return studyParticipantRepository.findByStudy_StudyRoomIdAndStatus(
                studyRoomId, ParticipationStatus.PENDING);
    }

// 참가 승인
    public void approveParticipant(Long participantId) {
        StudyParticipant participant = studyParticipantRepository.findById(participantId)
                .orElseThrow(() -> new ParticipantNotFoundException(participantId));

        participant.setStatus(ParticipationStatus.APPROVED);
        studyParticipantRepository.save(participant);

        log.info("참가자가 승인되었습니다. [participantId={}, userId={}]", participant.getId(), participant.getUserId());
    }

// 승인된 참가자 목록 조회

    public List<StudyParticipant> getApprovedParticipants(Long studyRoomId) {
        return studyParticipantRepository.findByStudy_StudyRoomIdAndStatus(
                studyRoomId, ParticipationStatus.APPROVED
        );
    }

    // 참가 거절

    public void rejectParticipant(Long participantId) {
        StudyParticipant participant = studyParticipantRepository.findById(participantId)
                .orElseThrow(() -> new ParticipantNotFoundException(participantId));

        participant.setStatus(ParticipationStatus.REJECTED);
        studyParticipantRepository.save(participant);

        log.info("참가자가 거절되었습니다. [participantId={}, userId={}]", participant.getId(), participant.getUserId());
    }


// 스터디 최대 인원 반환
    public int getMaxMembers(Long studyRoomId) {
        Study study = studyRepository.findById(studyRoomId)
                .orElseThrow(() -> new StudyNotFoundException(studyRoomId));
        return study.getMaxMembers();
    }


// 승인된 참가자 수 반환
    public int getApprovedCount(Long studyRoomId) {
        return getApprovedParticipants(studyRoomId).size();
    }


// 남은 자리 수 반환
    public int getRemainingSeats(Long studyRoomId) {
        return Math.max(getMaxMembers(studyRoomId) - getApprovedCount(studyRoomId), 0);
    }
}
