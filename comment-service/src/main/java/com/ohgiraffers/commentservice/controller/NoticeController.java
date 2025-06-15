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

    // ✅ 공지사항 작성
    @PostMapping("/{studyRoomId}")
    public ResponseEntity<String> create(@PathVariable Long studyRoomId,
                                         @RequestBody NoticeRequestDto dto) {
        try {
            if (dto.getTitle() == null || dto.getTitle().trim().isEmpty() ||
                    dto.getContent() == null || dto.getContent().trim().isEmpty()) {
                throw new IllegalArgumentException("비어있는 내용이 있습니다. 내용을 채워주세요.");
            }

            String writerId = noticeService.create(studyRoomId, dto);

            String responseMessage = String.format(
                    "공지사항 등록 완료\n작성자: %s\n제목: %s\n내용: %s",
                    writerId,
                    dto.getTitle(),
                    dto.getContent()
            );
            return ResponseEntity.ok(responseMessage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ 공지사항 수정
    @PutMapping("/{noticeId}")
    public ResponseEntity<String> update(@PathVariable Long noticeId,
                                         @RequestBody NoticeRequestDto dto) {
        try {
            Notice updatedNotice = noticeService.update(noticeId, dto);

            String message = String.format(
                    "공지사항 수정 완료\n작성자: %s\n제목: %s\n내용: %s",
                    updatedNotice.getWriterId(),
                    updatedNotice.getTitle(),
                    updatedNotice.getContent()
            );
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ 공지사항 삭제
    @DeleteMapping("/{noticeId}")
    public ResponseEntity<String> delete(@PathVariable Long noticeId) {
        try {
            noticeService.delete(noticeId);
            return ResponseEntity.ok("공지사항 삭제 완료");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
