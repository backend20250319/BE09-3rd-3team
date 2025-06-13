package io.studyit.userservice.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginRequest {
    private final String userId;
    private final String password;
}
