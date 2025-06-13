package com.ohgiraffers.studyservice.studyjoin.query.dto;

import com.ohgiraffers.studyservice.studyjoin.query.entity.StudyJoinListEntity;
import com.ohgiraffers.studyservice.studyjoin.command.entity.StudyJoinEntity;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class StudyJoinListDTO {

    private Long id;
    private Long userId;
    private Long studyId;
    private String studyTitle;
    private StudyJoinEntity.Status status;
    private LocalDateTime appliedAt;

    public static StudyJoinListDTO fromEntity(StudyJoinListEntity entity) {
        StudyJoinListDTO dto = new StudyJoinListDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setStudyId(entity.getStudyId());
        dto.setStudyTitle(entity.getStudyTitle());
        dto.setStatus(entity.getStatus());
        dto.setAppliedAt(entity.getAppliedAt());
        return dto;
    }

}

