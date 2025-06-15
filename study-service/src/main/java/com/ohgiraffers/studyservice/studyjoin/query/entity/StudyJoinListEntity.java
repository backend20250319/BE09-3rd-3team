package com.ohgiraffers.studyservice.studyjoin.query.entity;

import com.ohgiraffers.studyservice.studyjoin.command.entity.StudyJoinEntity.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "tbl_study_join_list")
@Immutable  // 읽기 전용 엔티티임을 명시 (Hibernate 전용)
public class StudyJoinListEntity {

    @Id
    private Long id;

    // userId 타입을 String 으로 통일 (기존 신청 엔티티와 맞춤)
    private String userId;

    private Long studyId;

    private String studyTitle;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime appliedAt;

}
