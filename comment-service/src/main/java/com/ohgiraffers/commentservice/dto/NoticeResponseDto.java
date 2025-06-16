package com.ohgiraffers.commentservice.dto;

import com.ohgiraffers.commentservice.entity.Notice;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NoticeResponseDto {
    private String title;
    private String content;
    private String writerId;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;

    public static NoticeResponseDto fromEntity(Notice notice) {
        NoticeResponseDto dto = new NoticeResponseDto();
        dto.setTitle(notice.getTitle());
        dto.setContent(notice.getContent());
        dto.setWriterId(notice.getWriterId());
        dto.setCreatedTime(notice.getCreatedTime());
        dto.setModifiedTime(notice.getModifiedTime());
        return dto;
    }
}
