package com.ohgiraffers.studyservice.study.dto;

import com.ohgiraffers.studyservice.study.entity.StudyStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 스터디 상태 조회 응답 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyStatusResponse {

    // 스터디룸 ID (PK)
    private Long studyRoomId;

    // 개설자 ID (organizer_id 컬럼, 고정값 "3")
    private String organizerId;

    // 사용자 ID (user_id 컬럼, 고정값 "2")
    private String userId;

    // 스터디 상태 (OPEN / CLOSED)
    private StudyStatus status;
}
