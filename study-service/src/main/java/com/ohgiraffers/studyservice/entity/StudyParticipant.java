package com.ohgiraffers.studyservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ Study 엔티티와 다대일 관계 설정
    @ManyToOne
    @JoinColumn(name = "study_id")
    private Study study;

    private Long userId;  // 참가한 사용자 ID

    @Enumerated(EnumType.STRING)
    private ParticipationStatus status; // PENDING, APPROVED, REJECTED

    private LocalDateTime appliedAt;

    @PrePersist
    protected void onCreate() {
        this.appliedAt = LocalDateTime.now();
    }
}
