package com.ohgiraffers.commentservice.service;

import com.ohgiraffers.commentservice.client.StudyClient;
import com.ohgiraffers.commentservice.dto.NoticeRequestDto;
import com.ohgiraffers.commentservice.dto.StudyStatusResponse;
import com.ohgiraffers.commentservice.entity.Notice;
import com.ohgiraffers.commentservice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final StudyClient studyClient;

    // ✅ 현재 요청자의 userId를 헤더에서 추출
    private String getCurrentUserId() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return attributes.getRequest().getHeader("X-User-Id");
        }
        throw new IllegalStateException("요청 헤더에 사용자 정보(X-User-Id)가 없습니다.");
    }

    // ✅ 공지사항 생성
    @Transactional
    public String create(Long studyRoomId, NoticeRequestDto dto) {
        String userId = getCurrentUserId();

        StudyStatusResponse studyStatus = studyClient.getStudyStatus(String.valueOf(studyRoomId));

        System.out.println("[DEBUG] Feign 응답 확인 ===================");
        System.out.println("organizerId (from studyStatus): " + studyStatus.getOrganizerId());
        System.out.println("userId (from studyStatus): " + studyStatus.getUserId());
        System.out.println("현재 로그인한 사용자 (from header): " + userId);
        System.out.println("========================================");

        String organizerId = studyStatus.getOrganizerId();
        String registeredUserId = studyStatus.getUserId();

        // ✅ 조건: organizerId가 3이고, 요청자가 실제 해당 userId여야 함
        if (!"3".equals(organizerId) || !userId.equals(registeredUserId)) {
            throw new IllegalArgumentException("공지사항 작성 권한이 없습니다. organizerId가 3인 사용자만 작성할 수 있습니다.");
        }

        Notice notice = new Notice();
        notice.setStudyRoomId(studyRoomId);
        notice.setWriterId(userId); // 실제 작성자 ID
        notice.setTitle(dto.getTitle());
        notice.setContent(dto.getContent());
        noticeRepository.save(notice);

        return userId;
    }

    // ✅ 공지사항 수정
    @Transactional
    public Notice update(Long id, NoticeRequestDto dto) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("공지사항이 존재하지 않습니다: id = " + id));

        // (선택) 권한 확인 로직 추가 가능

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

    // ✅ 공지사항 삭제
    @Transactional
    public void delete(Long id) {
        if (!noticeRepository.existsById(id)) {
            throw new IllegalArgumentException("공지사항이 존재하지 않습니다: id = " + id);
        }

        // (선택) 권한 확인 로직 추가 가능

        noticeRepository.deleteById(id);
    }
}
