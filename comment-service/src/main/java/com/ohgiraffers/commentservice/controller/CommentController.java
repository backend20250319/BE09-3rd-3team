package com.ohgiraffers.commentservice.controller;

import com.ohgiraffers.commentservice.dto.CommentRequestDto;
import com.ohgiraffers.commentservice.dto.CommentResponseDto;
import com.ohgiraffers.commentservice.entity.Comment;
import com.ohgiraffers.commentservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 1. 댓글 등록
    @PostMapping
    public ResponseEntity<CommentResponseDto> create(@RequestBody CommentRequestDto dto,
                                                     @AuthenticationPrincipal String userId) {
        try {
            dto.setCreatedUserId(userId);
            Comment comment = commentService.create(dto);
            CommentResponseDto response = CommentResponseDto.fromEntity(comment);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    // 2. 댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,
                                         @RequestBody CommentRequestDto dto) {
        try {
            Comment updatedComment = commentService.update(id, dto);
            String message = String.format(
                    "댓글 수정됨\n댓글 내용: %s", updatedComment.getContent()
            );
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // 3. 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.ok("댓글 삭제됨");
    }
}
