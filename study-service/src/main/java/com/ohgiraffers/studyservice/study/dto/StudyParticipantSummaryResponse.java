package com.ohgiraffers.studyservice.study.dto;

import com.ohgiraffers.studyservice.study.entity.StudyParticipant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyParticipantSummaryResponse {

    private List<StudyParticipant> approvedList; // 승인된 참가자 목록
    private int approvedCount;                  // 승인된 인원 수
    private int maxMembers;                     // 최대 인원 수
    private int remainingSeats;                 // 남은 자리 수
}
