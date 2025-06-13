package com.ohgiraffers.commentservice.dto;

import com.ohgiraffers.commentservice.entity.Comment;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponsetDto {
    private Long postId;
    private String createdUserId;
    private String content;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;

    public Comment toEntity() {
        return Comment.builder()
                .postId(postId)
                .createdUserId(createdUserId)
                .content(content)
                .build();
    }
}

