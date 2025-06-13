package com.ohgiraffers.studyservice.study.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class StudyUpdateRequest {

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "설명은 필수입니다.")
    private String description;

    @NotBlank(message = "주최자는 필수입니다.")
    private String organizer;

    @NotBlank(message = "카테고리는 필수입니다.")
    private String category;

    @Min(value = 1, message = "최대 인원은 1 이상이어야 합니다.")
    private int maxMembers;
}
