package com.ohgiraffers.commentservice.dto;

import com.ohgiraffers.commentservice.entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private Long postId;
    private String content;
    private String createdUserId;  // 작성자 ID
    private String createdUserName;

    public Comment toEntity() {
        return Comment.builder()
                .postId(postId)
                .createdUserId(createdUserId)
                .content(content)
                .build();
    }
}

