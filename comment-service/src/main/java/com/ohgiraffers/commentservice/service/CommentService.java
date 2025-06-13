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
    public void create(CommentRequestDto dto) {
        // 유저 서비스에서 이름 가져오기
//        UserDto userDto = userClient.getUserById(dto.getCreatedUserId());
        if (dto.getContent() == null || dto.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("댓글 내용이 비어있습니다. 댓글을 적어주세요!");
        }
        // 유저 이름을 dto나 엔티티에 설정
//        dto.setCreatedUserName(userDto.getName());
        commentRepository.save(dto.toEntity());
    }

    @Transactional
    public void update(Long id, CommentRequestDto dto) {
        Optional<Comment> optionalComment = commentRepository.findById(id);

        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            String existingContent = comment.getContent();
            String newContent = dto.getContent();

            // 변경 내용이 없으면 예외 처리
            if (existingContent != null && existingContent.trim().equals(newContent.trim())) {
                throw new IllegalArgumentException("전과 달라진 내용이 없습니다. 수정할 내용을 다시 입력해주세요");
            }

            comment.setContent(newContent);
            commentRepository.save(comment);
        } else {
            throw new IllegalArgumentException("댓글이 존재하지 않습니다: id = " + id);
        }
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
