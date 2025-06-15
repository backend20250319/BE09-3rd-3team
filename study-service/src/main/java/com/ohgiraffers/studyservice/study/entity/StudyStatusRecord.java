package com.ohgiraffers.studyservice.study.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "study_statuses")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class StudyStatusRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyRoomId;   // PK

    @Column(name = "organizer_id", nullable = false)
    private String organizerId;      // 기존 필드, 고정값 "3"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudyStatus status;      // OPEN, CLOSED


    @Column(name = "user_id", nullable = false)
    private String userId;           // 고정값 "2"


    @PrePersist
    protected void onCreate() {
        this.organizerId = "3";      // 생성 시 고정값
        this.userId      = "2";      // 생성 시 고정값
        if (this.status == null) {
            this.status = StudyStatus.OPEN;
        }
    }
}
