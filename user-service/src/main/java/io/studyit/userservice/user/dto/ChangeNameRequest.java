package io.studyit.userservice.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChangeNameRequest {
    private final String name;
}
