package io.studyit.userservice.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChangePasswordRequest {
    private final String currentPassword;
    private final String newPassword;
}
