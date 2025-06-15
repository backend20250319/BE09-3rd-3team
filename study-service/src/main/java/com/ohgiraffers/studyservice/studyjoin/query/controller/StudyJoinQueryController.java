package com.ohgiraffers.studyservice.studyjoin.query.controller;

import com.ohgiraffers.studyservice.studyjoin.query.dto.StudyJoinListDTO;
import com.ohgiraffers.studyservice.studyjoin.query.service.StudyJoinQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/study")
public class StudyJoinQueryController {

    private final StudyJoinQueryService studyJoinQueryService;

    @GetMapping("/list")
    public ResponseEntity<List<StudyJoinListDTO>> getStudyJoinListByUser(@AuthenticationPrincipal Long userId) {
        List<StudyJoinListDTO> result = studyJoinQueryService.findByUserId(userId);
        return ResponseEntity.ok(result);
    }
}
