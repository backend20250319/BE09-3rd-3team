package com.ohgiraffers.studyservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
//public class HeaderAuthenticationFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        // API Gateway가 전달한 헤더 읽기
//        String userId = request.getHeader("X-User-Id");
//
//        log.info("userId : {}", userId);
//
//        if (userId != null) {
//            // 이미 Gateway에서 검증된 정보로 인증 객체 구성
//            PreAuthenticatedAuthenticationToken authentication =
//                    new PreAuthenticatedAuthenticationToken(userId, null);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//        filterChain.doFilter(request, response);
//    }
//}


public class HeaderAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 개발 중이므로 무조건 통과 (혹은 임시 헤더 체크만)
        String userId = request.getHeader("X-User-Id");
        if (userId == null) {
            // 임시 유저 ID 설정
            userId = "123";
        }

        // SecurityContext 설정 안 해도 됨 → 인증 미사용으로 처리
        filterChain.doFilter(request, response);
    }
}
