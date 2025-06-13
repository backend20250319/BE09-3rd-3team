package com.ohgiraffers.studyservice.study.dto;

import com.ohgiraffers.studyservice.study.entity.StudyStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyStatusResponse {
    private Long studyRoomId;
    private String userId;
    private StudyStatus status;
}
