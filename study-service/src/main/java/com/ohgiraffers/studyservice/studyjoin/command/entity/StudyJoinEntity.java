package com.ohgiraffers.studyservice.studyjoin.command.entity;

import com.ohgiraffers.studyservice.study.entity.Study;
import com.ohgiraffers.studyservice.studyjoin.command.dto.StudyJoinRequestDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_study_join")
@Getter
@Setter
@NoArgsConstructor
public class StudyJoinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_room_id")
    private Study study;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }

    @Builder
    public StudyJoinEntity(String userId, Study study, Status status, LocalDateTime createdAt) {
        this.userId = userId;
        this.study = study;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static StudyJoinEntity of(StudyJoinRequestDTO dto, String userId, LocalDateTime now) {
        Study study = new Study();
        study.setStudyRoomId(dto.getStudyRoomId());

        return StudyJoinEntity.builder()
                .userId(userId)
                .study(study)
                .status(Status.PENDING)
                .createdAt(now)
                .build();
    }
}


