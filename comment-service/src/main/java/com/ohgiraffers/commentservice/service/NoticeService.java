package com.ohgiraffers.commentservice.service;

import com.ohgiraffers.commentservice.dto.NoticeRequestDto;
import com.ohgiraffers.commentservice.entity.Notice;
import com.ohgiraffers.commentservice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional
    public void create(Long studyRoomId, NoticeRequestDto dto) {
        Notice notice = new Notice();
        notice.setStudyRoomId(studyRoomId);
        notice.setWriterId(dto.getWriterId());
        notice.setTitle(dto.getTitle());
        notice.setContent(dto.getContent());
        noticeRepository.save(notice);
    }

    @Transactional
    public Notice update(Long id, NoticeRequestDto dto) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("공지사항이 존재하지 않습니다: id = " + id));

        // 제목/내용이 모두 기존과 동일하거나, 내용이 비어 있으면 예외 발생
        boolean isSameTitle = notice.getTitle().equals(dto.getTitle());
        boolean isSameContent = notice.getContent().equals(dto.getContent());
        boolean isContentBlank = dto.getContent() == null || dto.getContent().trim().isEmpty();

        if ((isSameTitle && isSameContent) || isContentBlank) {
            throw new IllegalArgumentException("수정된 내용이 없습니다. 다시 수정할 내용을 입력해주세요.");
        }

        notice.setTitle(dto.getTitle());
        notice.setContent(dto.getContent());

        return noticeRepository.save(notice);
    }


    @Transactional
    public void delete(Long id) {
        if (!noticeRepository.existsById(id)) {
            throw new IllegalArgumentException("공지사항이 존재하지 않습니다: id = " + id);
        }
        noticeRepository.deleteById(id);
    }
}
