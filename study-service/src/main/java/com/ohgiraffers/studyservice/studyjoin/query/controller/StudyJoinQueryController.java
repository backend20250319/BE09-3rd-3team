package com.ohgiraffers.studyservice.studyjoin.query.controller;

import com.ohgiraffers.studyservice.studyjoin.query.dto.StudyJoinListDTO;
import com.ohgiraffers.studyservice.studyjoin.query.service.StudyJoinQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/list")
public class StudyJoinQueryController {

    private final StudyJoinQueryService studyJoinQueryService;

    @GetMapping("/{userId}")
//    public ResponseEntity<List<StudyJoinListDTO>> getStudyJoinListByUser(@PathVariable Long userId) {
//        List<StudyJoinListDTO> result = studyJoinQueryService.findByUserId(userId);
//        return ResponseEntity.ok(result);
//    }
    public ResponseEntity<List<StudyJoinListDTO>> getStudyJoinListByUser() {
        Long testUserId = 123L; // 임시 userId
        List<StudyJoinListDTO> result = studyJoinQueryService.findByUserId(testUserId);
        return ResponseEntity.ok(result);
    }

}
