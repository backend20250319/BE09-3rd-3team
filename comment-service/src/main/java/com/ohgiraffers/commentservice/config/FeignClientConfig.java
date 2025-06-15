package com.ohgiraffers.commentservice.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {

            ServletRequestAttributes requestAttributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (requestAttributes != null) {

                // ✅ 테스트용: Authorization 헤더 강제 삽입
                requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer test-token");

                // 기존 X-User-Id 유지
                String userId = requestAttributes.getRequest().getHeader("X-User-Id");
                if (userId != null) {
                    requestTemplate.header("X-User-Id", userId);
                }
            }
        };
    }
}
