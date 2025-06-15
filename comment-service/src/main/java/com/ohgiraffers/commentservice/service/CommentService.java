package com.ohgiraffers.commentservice.service;

import com.ohgiraffers.commentservice.dto.CommentRequestDto;
import com.ohgiraffers.commentservice.entity.Comment;
import com.ohgiraffers.commentservice.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Comment create(CommentRequestDto dto) {
        if (dto.getContent() == null || dto.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("댓글 내용이 비어있습니다. 댓글을 적어주세요!");
        }

        // 대댓글일 경우 부모 댓글 유효성 확인
        if (dto.getParentId() != null) {
            Optional<Comment> parentComment = commentRepository.findById(dto.getParentId());
            if (parentComment.isEmpty()) {
                throw new IllegalArgumentException("부모 댓글이 존재하지 않습니다: id = " + dto.getParentId());
            }
        }

        return commentRepository.save(dto.toEntity());
    }

    @Transactional
    public Comment createReply(Long parentId, CommentRequestDto dto) {
        Optional<Comment> parentComment = commentRepository.findById(parentId);
        if (parentComment.isEmpty()) {
            throw new IllegalArgumentException("부모 댓글이 존재하지 않습니다: id = " + parentId);
        }

        if (dto.getContent() == null || dto.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("대댓글 내용이 비어있습니다. 내용을 입력해주세요!");
        }

        dto.setParentId(parentId);
        return commentRepository.save(dto.toEntity());
    }

    @Transactional
    public Comment update(Long id, CommentRequestDto dto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다: id = " + id));

        if (dto.getContent() == null || dto.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("수정할 댓글 내용이 비어있습니다.");
        }

        if (comment.getContent().equals(dto.getContent())) {
            throw new IllegalArgumentException("수정된 내용이 없습니다. 다시 수정할 내용을 입력해주세요");
        }

        comment.setContent(dto.getContent());
        return commentRepository.save(comment);
    }

    @Transactional
    public void delete(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new IllegalArgumentException("댓글이 존재하지 않습니다: id = " + id);
        }
        commentRepository.deleteById(id);
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }
}
