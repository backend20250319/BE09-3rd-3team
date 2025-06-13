package com.ohgiraffers.studyservice.study.service;

import com.ohgiraffers.studyservice.study.dto.StudyCreateRequest;
import com.ohgiraffers.studyservice.study.dto.StudyResponse;
import com.ohgiraffers.studyservice.study.dto.StudyUpdateRequest;
import com.ohgiraffers.studyservice.study.entity.Study;
import com.ohgiraffers.studyservice.study.entity.StudyStatus;
import com.ohgiraffers.studyservice.study.exception.StudyNotFoundException;
import com.ohgiraffers.studyservice.study.repasitory.StudyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;

    // 스터디 개설
    public StudyResponse createStudy(StudyCreateRequest request) {
        Study study = Study.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .organizer(request.getOrganizer())
                .category(request.getCategory())
                .maxMembers(request.getMaxMembers())
                .closed(false)
                .status(StudyStatus.OPEN)
                .build();

        Study saved = studyRepository.save(study);
        log.info("스터디가 생성되었습니다. [postId={}, title={}]", saved.getStudyRoomId(), saved.getTitle());
        return StudyResponse.from(saved);
    }

    // 전체 스터디 목록 조회
    public List<StudyResponse> getAllStudies() {
        return studyRepository.findAll().stream()
                .map(StudyResponse::from)
                .collect(Collectors.toList());
    }

    // studyRoomID로 스터디 상세 조회
    public StudyResponse getStudyById(Long studyRoomId) {
        Study study = studyRepository.findById(studyRoomId)
                .orElseThrow(() -> new StudyNotFoundException(studyRoomId));
        return StudyResponse.from(study);
    }

    // 스터디 마감
    public void closeStudy(Long studyRoomId) {
        Study study = studyRepository.findById(studyRoomId)
                .orElseThrow(() -> new StudyNotFoundException(studyRoomId));

        study.setClosed(true);
        study.setStatus(StudyStatus.CLOSED);
        studyRepository.save(study);

        log.info("스터디가 마감되었습니다. [postId={}, title={}]", study.getStudyRoomId(), study.getTitle());
    }

    // 스터디 내용 수정
    public StudyResponse updateStudy(Long studyRoomId, StudyUpdateRequest request) {
        Study study = studyRepository.findById(studyRoomId)
                .orElseThrow(() -> new StudyNotFoundException(studyRoomId));

        study.setTitle(request.getTitle());
        study.setDescription(request.getDescription());
        study.setCategory(request.getCategory());
        study.setMaxMembers(request.getMaxMembers());

        Study updated = studyRepository.save(study);
        log.info("스터디를 수정하였습니다. [postId={}, title={}]", updated.getStudyRoomId(), updated.getTitle());
        return StudyResponse.from(updated);
    }

    // 스터디 삭제
    public void deleteStudy(Long studyRoomId) {
        Study study = studyRepository.findById(studyRoomId)
                .orElseThrow(() -> new StudyNotFoundException(studyRoomId));

        studyRepository.delete(study);
        log.info("스터디가 삭제되었습니다. [postId={}, title={}]", study.getStudyRoomId(), study.getTitle());
    }

    // 제목 또는 카테고리 키워드로 검색
    public List<StudyResponse> searchStudiesByKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("검색어를 입력해주세요.");
        }

        String lowerKeyword = keyword.toLowerCase();

        List<StudyResponse> result = studyRepository.findAll().stream()
                .filter(study ->
                        study.getTitle().toLowerCase().contains(lowerKeyword) ||
                                study.getCategory().toLowerCase().contains(lowerKeyword)
                )
                .map(StudyResponse::from)
                .collect(Collectors.toList());

        log.info("'{}' 키워드로 검색된 스터디 {}건", keyword, result.size());
        return result;
    }


}
