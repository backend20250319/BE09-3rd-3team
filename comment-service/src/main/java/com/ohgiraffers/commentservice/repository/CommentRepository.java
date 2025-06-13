package com.ohgiraffers.commentservice.repository;

import com.ohgiraffers.commentservice.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
