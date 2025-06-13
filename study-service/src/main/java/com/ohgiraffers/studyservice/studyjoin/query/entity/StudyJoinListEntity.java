package com.ohgiraffers.studyservice.studyjoin.query.entity;

import com.ohgiraffers.studyservice.studyjoin.command.entity.StudyJoinEntity.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "tbl_study_join_list")  // 실제 테이블명으로 수정
public class StudyJoinListEntity {

    @Id
    private Long id;

    private Long userId;

    private Long studyId;

    private String studyTitle;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime appliedAt;

}
