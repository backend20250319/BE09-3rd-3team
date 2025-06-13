package com.ohgiraffers.commentservice.controller;

import com.ohgiraffers.commentservice.dto.NoticeRequestDto;
import com.ohgiraffers.commentservice.entity.Notice;
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
        String responseMessage = String.format(
                "공지사항 등록 완료\n제목: %s\n내용: %s",
                dto.getTitle(),
                dto.getContent()
        );
        return ResponseEntity.ok(responseMessage);
    }

    @PutMapping("/{noticeId}")
    public ResponseEntity<String> update(@PathVariable Long noticeId,
                                         @RequestBody NoticeRequestDto dto) {
        Notice updatedNotice = noticeService.update(noticeId, dto);

        String message = String.format(
                "공지사항 수정 완료\n제목: %s\n내용: %s",
                updatedNotice.getTitle(),
                updatedNotice.getContent()
        );

        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{noticeId}")
    public ResponseEntity<String> delete(@PathVariable Long noticeId) {
        noticeService.delete(noticeId);
        return ResponseEntity.ok("공지사항 삭제 완료");
    }
}