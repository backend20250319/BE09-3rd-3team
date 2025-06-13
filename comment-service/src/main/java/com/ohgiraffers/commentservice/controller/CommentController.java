package com.ohgiraffers.commentservice.controller;

import com.ohgiraffers.commentservice.dto.CommentRequestDto;
import com.ohgiraffers.commentservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 1. 댓글 등록
    @PostMapping
    public ResponseEntity<String> create(@RequestBody CommentRequestDto dto) {
        try {
            dto.setCreatedUserId("test");
            commentService.create(dto);
            String message = "댓글 등록됨\n댓글: " + dto.getContent();
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 2. 댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,
                                         @RequestBody CommentRequestDto dto) {
        try {
            commentService.update(id, dto);
            return ResponseEntity.ok("댓글 수정됨");
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
