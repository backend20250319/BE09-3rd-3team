package com.ohgiraffers.commentservice.repository;

import com.ohgiraffers.commentservice.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 자식 댓글(대댓글) 조회
    List<Comment> findByParentId(Long parentId);

    // 특정 게시글(postId)의 댓글 조회
    List<Comment> findByPostId(Long postId);
}
