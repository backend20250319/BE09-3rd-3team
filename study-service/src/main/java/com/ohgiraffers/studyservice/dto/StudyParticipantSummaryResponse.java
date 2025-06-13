package com.ohgiraffers.studyservice.dto;

import com.ohgiraffers.studyservice.entity.StudyParticipant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class StudyParticipantSummaryResponse {

    private List<StudyParticipant> approvedList; // 승인된 참가자 목록
    private int approvedCount;                  // 승인된 인원 수
    private int maxMembers;                     // 최대 인원 수
    private int remainingSeats;                 // 남은 자리 수
}
