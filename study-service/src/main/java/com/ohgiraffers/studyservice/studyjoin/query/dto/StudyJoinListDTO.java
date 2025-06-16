package com.ohgiraffers.studyservice.studyjoin.query.dto;

import com.ohgiraffers.studyservice.studyjoin.command.entity.StudyJoinEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class StudyJoinListDTO {
    private Long id;
    private Long studyRoomId;
    private String title;
    private String description;
    private String category;
    private String status;
    private LocalDateTime createdAt;

    public static StudyJoinListDTO fromEntity(StudyJoinEntity entity) {
        return new StudyJoinListDTO(
                entity.getId(),
                entity.getStudy().getStudyRoomId(),
                entity.getStudy().getTitle(),
                entity.getStudy().getDescription(),
                entity.getStudy().getCategory(),
                entity.getStatus().name(),
                entity.getCreatedAt()
        );
    }
}
