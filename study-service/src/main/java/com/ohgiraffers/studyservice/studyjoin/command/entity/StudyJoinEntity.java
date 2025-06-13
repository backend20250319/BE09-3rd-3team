package com.ohgiraffers.studyservice.studyjoin.command.entity;

import com.ohgiraffers.studyservice.studyjoin.command.dto.StudyJoinRequestDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "tbl_study_join")
public class StudyJoinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;        // 신청한 사용자 ID (외부에서 받아옴)
    private Long studyId;       // 지원한 스터디 ID

    @Enumerated(EnumType.STRING)
    private Status status;      // PENDING, APPROVED, REJECTED

    private LocalDateTime createdAt ;

    public enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }

    public static StudyJoinEntity of(StudyJoinRequestDTO dto, Long userId, LocalDateTime now) {
        StudyJoinEntity entity = new StudyJoinEntity();
        entity.setUserId(userId);
        entity.setStudyId(dto.getStudyId());
        entity.setStatus(Status.PENDING);
        entity.setCreatedAt (now);
        return entity;
    }
}
