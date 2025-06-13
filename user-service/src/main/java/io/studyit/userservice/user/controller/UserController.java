package io.studyit.userservice.user.controller;

import io.studyit.userservice.common.ApiResponse;
import io.studyit.userservice.user.dto.*;
import io.studyit.userservice.user.security.UserDetailsImpl;
import io.studyit.userservice.user.service.AuthService;
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

    private final AuthService authService;
    private final UserCommandService userCommandService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody UserCreateRequest request) {
        userCommandService.registerUser(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.successWithMessage("회원가입이 성공적으로 완료되었습니다."));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@RequestBody LoginRequest request) {
        TokenResponse token = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success(token, "로그인이 성공적으로 완료되었습니다."));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestBody RefreshTokenRequest request) {
        authService.logout(request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.successWithMessage("로그아웃이 성공적으로 완료되었습니다."));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponse>> refreshToken(@RequestBody RefreshTokenRequest request) {
        TokenResponse response = authService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success(response, "토큰이 성공적으로 갱신되었습니다."));
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
            @RequestBody ChangeNameRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userCommandService.changeName(id, request, userDetails);
        return ResponseEntity.ok(ApiResponse.successWithMessage("이름이 성공적으로 변경되었습니다."));
    }
}
