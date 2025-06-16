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
    private String organizerId; // 고정값 "3"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudyStatus status; // OPEN, CLOSED

    @Column(name = "user_id", nullable = false)
    private String userId;      // Gateway에서 전달받은 로그인 사용자 ID

    @PrePersist
    protected void onCreate() {
        this.organizerId = "3";      // 고정 organizer ID
        if (this.status == null) {
            this.status = StudyStatus.OPEN;
        }
        // userId는 외부에서 주입됨 → 여기서 설정 X
    }
}
