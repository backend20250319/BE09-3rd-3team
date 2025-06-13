package com.ohgiraffers.studyservice.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyCreateRequest {

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "설명은 필수입니다.")
    private String description;

    @NotBlank(message = "주최자는 필수입니다.")
    private String organizer;

    @NotBlank(message = "카테고리는 필수입니다.")
    private String category;

    @Min(value = 1, message = "최소 인원은 1명 이상이어야 합니다.")
    private int maxMembers;
}
