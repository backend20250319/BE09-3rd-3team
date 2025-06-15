package com.ohgiraffers.studyservice.studyjoin.query.controller;

import com.ohgiraffers.studyservice.common.ApiResponse;
import com.ohgiraffers.studyservice.studyjoin.command.entity.StudyJoinEntity;
import com.ohgiraffers.studyservice.studyjoin.command.service.StudyJoinService;
import com.ohgiraffers.studyservice.studyjoin.query.dto.StudyJoinListDTO;
import com.ohgiraffers.studyservice.studyjoin.query.service.StudyJoinQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/study")
public class StudyJoinQueryController {

    private final StudyJoinQueryService studyJoinQueryService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<?>> getMyStudyJoins(@AuthenticationPrincipal String userId) {

        List<StudyJoinListDTO> result = studyJoinQueryService.getMyStudyJoins(userId);

        if (result.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.success("신청한 스터디가 없습니다."));
        }

        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
