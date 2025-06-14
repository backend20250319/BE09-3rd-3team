package com.ohgiraffers.studyservice.study.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "study_statuses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyStatusRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyRoomId;   // PK

    @Column(name = "organizer_id", nullable = false)
    private String organizerId;      // 고정값 "2"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudyStatus status; // OPEN, CLOSED

    @PrePersist
    protected void onCreate() {
        this.organizerId = "2";               // 생성 시 고정값
        if (this.status == null) {
            this.status = StudyStatus.OPEN;  // 기본 상태 설정
        }
    }
}
