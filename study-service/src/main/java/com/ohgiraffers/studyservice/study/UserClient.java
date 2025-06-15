package com.ohgiraffers.studyservice.study;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/users/me")
    UserResponse getCurrentUser(@RequestHeader("Authorization") String token);
}
