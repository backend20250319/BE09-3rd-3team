package com.ohgiraffers.commentservice.repository;

import com.ohgiraffers.commentservice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
