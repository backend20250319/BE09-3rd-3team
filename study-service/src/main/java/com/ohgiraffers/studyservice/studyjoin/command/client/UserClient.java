package com.ohgiraffers.studyservice.studyjoin.command.client;

import com.ohgiraffers.studyservice.common.ApiResponse;
import com.ohgiraffers.studyservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 1. Gateway 를 호출하는 상황 (order -> gateway -> user)
// @FeignClient(name = "user-service", url = "http://localhost:8000", configuration = FeignClientConfig.class)
// 2. 내부에서 user-service를 호출하는 상황 (order -> user)
@FeignClient(name = "studyit-user-service", configuration = FeignClientConfig.class)
public interface UserClient {

    // User Service에서 사용자 상태나 간단한 정보를 조회하는 API
    // 1. Gateway를 호출하는 상황
//    @GetMapping("/api/v1/user-service/users/{userId}/grade")

    // 2. 내부에서 user-service를 호출하는 상황
    @GetMapping("/users/{userId}/grade")
    ApiResponse<String> getUserGrade(@PathVariable("userId") Long userId);
    
    // 유저 서비스에서 필요한 정보를 요청 ex)role 타입이나, 유저 정보
    

}
