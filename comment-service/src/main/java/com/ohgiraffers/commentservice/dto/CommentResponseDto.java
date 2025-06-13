package com.ohgiraffers.commentservice.dto;

import com.ohgiraffers.commentservice.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto {
    private Long postId;
    private String createdUserId;
    private String content;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;

    public static CommentResponseDto fromEntity(Comment comment) {
        CommentResponseDto dto = new CommentResponseDto();
        dto.setPostId(comment.getPostId());
        dto.setCreatedUserId(comment.getCreatedUserId());
        dto.setContent(comment.getContent());
        dto.setCreatedTime(comment.getCreatedTime());
        dto.setModifiedTime(comment.getModifiedTime());
        return dto;
    }
}


