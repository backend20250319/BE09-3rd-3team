package com.ohgiraffers.commentservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class NoticeRequestDto {
    private String title;
    private String content;
    private String writerId; // 방장 Id
}

