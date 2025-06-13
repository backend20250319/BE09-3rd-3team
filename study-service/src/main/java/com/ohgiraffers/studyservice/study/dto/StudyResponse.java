package com.ohgiraffers.studyservice.study.dto;

import com.ohgiraffers.studyservice.study.entity.Study;
import com.ohgiraffers.studyservice.study.entity.StudyStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudyResponse {

    private Long studyRoomId;             // 스터디룸 ID
    private String title;                 // 제목
    private String description;           // 설명
    private String organizer;             // 개설자
    private StudyStatus status;           // 상태 (OPEN / CLOSED)
    private String category;              // 카테고리
    private int maxMembers;               // 최대 인원
    private String createdAtFormatted;    // ✅ 생성일 포맷
    private String closedAtFormatted;     // ✅ 마감일 포맷

    public static StudyResponse from(Study study) {
        return StudyResponse.builder()
                .studyRoomId(study.getStudyRoomId())
                .title(study.getTitle())
                .description(study.getDescription())
                .organizer(study.getOrganizer())
                .status(study.getStatus())
                .category(study.getCategory())
                .maxMembers(study.getMaxMembers())
                .createdAtFormatted(study.getFormattedCreatedAt())   // ✅ 생성일 포맷
                .closedAtFormatted(study.getFormattedClosedAt())     // ✅ 마감일 포맷
                .build();
    }
}
