package com.ohgiraffers.commentservice.client;

import com.ohgiraffers.commentservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/users/me")
    UserDto getUserInfo(@RequestHeader("Authorization") String token);
}