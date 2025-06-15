package com.ohgiraffers.commentservice.service;

//import com.ohgiraffers.commentservice.client.UserClient;
import com.ohgiraffers.commentservice.dto.CommentRequestDto;
import com.ohgiraffers.commentservice.entity.Comment;
import com.ohgiraffers.commentservice.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor


public class CommentService {
    // 댓글 등록
    private final CommentRepository commentRepository;
//    private final UserClient userClient;

    @Transactional
    public Comment create(CommentRequestDto dto) {
        if (dto.getContent() == null || dto.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("댓글 내용이 비어있습니다. 댓글을 적어주세요!");
        }

        // 유저 이름을 외부에서 받아올 경우 사용
        // UserDto userDto = userClient.getUserById(dto.getCreatedUserId());
        // dto.setCreatedUserName(userDto.getName());
        Comment savedComment = commentRepository.save(dto.toEntity());
        return savedComment;
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
    // 댓글 삭제
    public void delete(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new IllegalArgumentException("댓글이 존재하지 않습니다: id = " + id);
        }
        commentRepository.deleteById(id);
    }
}
