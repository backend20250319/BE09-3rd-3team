package com.ohgiraffers.studyservice.studyjoin.command.controller;

import com.ohgiraffers.studyservice.common.ApiResponse;
import com.ohgiraffers.studyservice.studyjoin.command.dto.StudyJoinRequestDTO;
import com.ohgiraffers.studyservice.studyjoin.command.service.StudyJoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
public class StudyJoinCommandController {

    private final StudyJoinService studyJoinService;

    // 스터디 참여 신청
    @PostMapping("/join")
    public ResponseEntity<ApiResponse<String>> joinStudy(
            @AuthenticationPrincipal String userId,
            @RequestBody StudyJoinRequestDTO requestDTO) {

        try {
            studyJoinService.joinStudy(requestDTO, userId);
            String message = "스터디 참여 신청이 완료되었습니다.";
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponse.success(message));
        } catch (IllegalArgumentException e) {
            String errMsg = "이미 신청한 스터디 입니다.";
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.failure("DUPLICATE_STUDY", errMsg));
        }
    }
}
