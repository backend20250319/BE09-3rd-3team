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

    @Column(name = "user_id")
    private String userId;

    private Long studyRoomId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }

    public static StudyJoinEntity of(StudyJoinRequestDTO dto, String userId, LocalDateTime now) {
        StudyJoinEntity entity = new StudyJoinEntity();
        entity.setUserId(userId);
        entity.setStudyRoomId(dto.getStudyRoomId());
        entity.setStatus(Status.PENDING);
        entity.setCreatedAt(now);
        return entity;
    }
}

