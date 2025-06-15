package com.ohgiraffers.commentservice.controller;

import com.ohgiraffers.commentservice.dto.CommentRequestDto;
import com.ohgiraffers.commentservice.dto.CommentResponseDto;
import com.ohgiraffers.commentservice.entity.Comment;
import com.ohgiraffers.commentservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 1. 댓글 또는 대댓글 등록
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

    // 1-1. 대댓글 등록
    @PostMapping("/{parentId}/reply")
    public ResponseEntity<CommentResponseDto> reply(@PathVariable Long parentId,
                                                    @RequestBody CommentRequestDto dto,
                                                    @AuthenticationPrincipal String userId) {
        try {
            dto.setCreatedUserId(userId);
            Comment reply = commentService.createReply(parentId, dto);
            CommentResponseDto response = CommentResponseDto.fromEntity(reply);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    // 2. 댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,
                                         @RequestBody CommentRequestDto dto) {
        try {
            Comment updatedComment = commentService.update(id, dto);
            String message = String.format("댓글 수정됨\n댓글 내용: %s", updatedComment.getContent());
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

    // 4. 특정 게시글의 전체 댓글 및 대댓글 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponseDto>> getAllCommentsByPost(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        List<CommentResponseDto> response = comments.stream()
                .map(CommentResponseDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
