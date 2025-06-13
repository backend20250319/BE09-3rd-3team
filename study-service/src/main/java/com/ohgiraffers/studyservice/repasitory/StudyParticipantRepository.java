package com.ohgiraffers.studyservice.repasitory;

import com.ohgiraffers.studyservice.entity.ParticipationStatus;
import com.ohgiraffers.studyservice.entity.StudyParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyParticipantRepository extends JpaRepository<StudyParticipant, Long> {

    // ✅ 특정 스터디의 특정 상태인 참가자 조회
    List<StudyParticipant> findByStudy_StudyRoomIdAndStatus(Long studyRoomId, ParticipationStatus status);
}
