package com.ohgiraffers.studyservice.study.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudyApplyRequest {

    @NotNull(message = "스터디 ID는 필수입니다.")
    @Min(value = 1, message = "스터디 ID는 1 이상이어야 합니다.")
    private Long studyRoomId;

    @NotNull(message = "사용자 ID는 필수입니다.")
    @Min(value = 1, message = "사용자 ID는 1 이상이어야 합니다.")
    private Long userId;
}
