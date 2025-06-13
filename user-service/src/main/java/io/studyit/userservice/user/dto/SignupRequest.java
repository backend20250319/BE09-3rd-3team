package io.studyit.userservice.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignupRequest {
    private final String userId;
    private final String password;
    private final String name;
}
