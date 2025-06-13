package io.studyit.userservice.user.controller;

import io.studyit.userservice.common.ApiResponse;
import io.studyit.userservice.user.dto.ChangeNameRequest;
import io.studyit.userservice.user.dto.ChangePasswordRequest;
import io.studyit.userservice.user.dto.UserCreateRequest;
import io.studyit.userservice.user.security.UserDetailsImpl;
import io.studyit.userservice.user.service.UserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserCommandService userCommandService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody UserCreateRequest request) {
        userCommandService.registerUser(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.successWithMessage("회원가입이 성공적으로 완료되었습니다."));
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userCommandService.changePassword(id, request, userDetails);
        return ResponseEntity.ok(ApiResponse.successWithMessage("비밀번호가 성공적으로 변경되었습니다."));
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<ApiResponse<Void>> changeName(
            @PathVariable Long id,
            @RequestBody ChangeNameRequest request) {
        userCommandService.changeName(id, request);
        return ResponseEntity.ok(ApiResponse.successWithMessage("이름이 성공적으로 변경되었습니다."));
    }
}
