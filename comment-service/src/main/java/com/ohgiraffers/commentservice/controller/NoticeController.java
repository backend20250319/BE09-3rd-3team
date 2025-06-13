package com.ohgiraffers.commentservice.controller;

import com.ohgiraffers.commentservice.dto.NoticeRequestDto;
import com.ohgiraffers.commentservice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping("/{studyRoomId}")
    public ResponseEntity<String> create(@PathVariable Long studyRoomId,
                                         @RequestBody NoticeRequestDto dto) {
        noticeService.create(studyRoomId, dto);
        return ResponseEntity.ok("공지사항 등록 완료");
    }

    @PutMapping("/{noticeId}")
    public ResponseEntity<String> update(@PathVariable Long noticeId,
                                         @RequestBody NoticeRequestDto dto) {
        noticeService.update(noticeId, dto);
        return ResponseEntity.ok("공지사항 수정 완료");
    }

    @DeleteMapping("/{noticeId}")
    public ResponseEntity<String> delete(@PathVariable Long noticeId) {
        noticeService.delete(noticeId);
        return ResponseEntity.ok("공지사항 삭제 완료");
    }
}