package com.ohgiraffers.studyservice.dto;

import com.ohgiraffers.studyservice.entity.Study;
import com.ohgiraffers.studyservice.entity.StudyStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudyResponse {

    private Long studyRoomId;           // 스터디룸 ID
    private String title;               // 제목
    private String description;         // 설명
    private String organizer;           // 개설자
    private boolean closed;             // 마감 여부
    private StudyStatus status;         // 상태 (OPEN / CLOSED)
    private String category;            // 카테고리
    private int maxMembers;             // 최대 인원
    private String createdAtFormatted;  // ✅ 포맷된 생성일시

    public static StudyResponse from(Study study) {
        return StudyResponse.builder()
                .studyRoomId(study.getStudyRoomId())
                .title(study.getTitle())
                .description(study.getDescription())
                .organizer(study.getOrganizer())
                .closed(study.isClosed())
                .status(study.getStatus())
                .category(study.getCategory())
                .maxMembers(study.getMaxMembers())
                .createdAtFormatted(study.getFormattedCreatedAt())  // ✅ 한글 포맷 적용
                .build();
    }
}
