package com.ohgiraffers.studyservice.dto;

import com.ohgiraffers.studyservice.entity.StudyParticipant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class StudyApprovedResponse {
    private Long studyRoomId;
    private int maxMembers;
    private int approvedCount;
    private int remainingSeats;
    private List<StudyParticipant> approvedParticipants;
}
