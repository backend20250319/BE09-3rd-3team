package com.ohgiraffers.studyservice.study.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyRoomId;              // 스터디룸 ID

    private String title;                  // 스터디 제목
    private String description;            // 설명
    private String organizer;              // 개설자 이름

    @Enumerated(EnumType.STRING)
    private StudyStatus status;            // 스터디 상태 (OPEN, CLOSED)

    private String category;               // ex) Java, Spring, SQL 등
    private int maxMembers;                // 최대 인원 수

    private String userId;                // ✅ 고정된 코드

    @Column(updatable = false)
    private LocalDateTime createdAt;       // 생성일시

    private LocalDateTime closedAt; // 마감 일시

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.userId = "2";               // 항상 2로 고정
    }

    public String getFormattedCreatedAt() {
        if (createdAt == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초");
        return createdAt.format(formatter);
    }

    public String getFormattedClosedAt() {
        if (closedAt == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초");
        return closedAt.format(formatter);
    }
}
