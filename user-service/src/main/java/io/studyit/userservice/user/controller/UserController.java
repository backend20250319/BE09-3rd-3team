package io.studyit.userservice.user.controller;

import io.studyit.userservice.common.ApiResponse;
import io.studyit.userservice.user.dto.*;
import io.studyit.userservice.user.security.UserDetailsImpl;
import io.studyit.userservice.user.service.UserService;
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

    private final UserService userService;
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
        TokenResponse token = userService.login(request);
        return ResponseEntity.ok(ApiResponse.success(token, "로그인이 성공적으로 완료되었습니다."));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestBody RefreshTokenRequest request) {
        userService.logout(request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.successWithMessage("로그아웃이 성공적으로 완료되었습니다."));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponse>> refreshToken(@RequestBody RefreshTokenRequest request) {
        TokenResponse response = userService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success(response, "토큰이 성공적으로 갱신되었습니다."));
    }

    @PatchMapping("/{userId}/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @PathVariable String userId,
            @RequestBody ChangePasswordRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userCommandService.changePassword(userId, request, userDetails);
        return ResponseEntity.ok(ApiResponse.successWithMessage("비밀번호가 성공적으로 변경되었습니다."));
    }

    @PatchMapping("/{userId}/name")
    public ResponseEntity<ApiResponse<Void>> changeName(
            @PathVariable String userId,
            @RequestBody ChangeNameRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userCommandService.changeName(userId, request, userDetails);
        return ResponseEntity.ok(ApiResponse.successWithMessage("이름이 성공적으로 변경되었습니다."));
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @PathVariable String userId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userCommandService.deleteUser(userId, userDetails);
        return ResponseEntity.ok(ApiResponse.successWithMessage("회원 탈퇴가 완료되었습니다."));
    }
}
