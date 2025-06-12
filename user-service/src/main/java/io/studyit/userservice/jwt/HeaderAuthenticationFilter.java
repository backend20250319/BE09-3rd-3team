    package io.studyit.userservice.jwt;

    import jakarta.servlet.FilterChain;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
    import org.springframework.web.filter.OncePerRequestFilter;

    import java.io.IOException;
    import java.util.List;

    @Slf4j
    public class HeaderAuthenticationFilter extends OncePerRequestFilter {

        @Override
        protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
            String path = request.getServletPath();
            String method = request.getMethod();

            log.info("shouldNotFilter ➡️➡️➡️➡️ path: {}, method: {}", path, method);

            // 회원가입 요청은 필터 동작 안 하도록 처리
            if ("/user".equals(path) && "POST".equalsIgnoreCase(method)) {
                return true;
            }

            // 필요하면 인증 제외할 다른 경로 및 메서드 추가 가능
            return false;
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            // API Gateway가 전달한 헤더 읽기
            String userId = request.getHeader("X-User-Id");
            String role = request.getHeader("X-User-Role");

            log.info("userId : {}", userId);
            log.info("role : {}", role);

            if (userId != null && role != null) {
                // 이미 Gateway에서 검증된 정보로 인증 객체 구성
                PreAuthenticatedAuthenticationToken authentication =
                        new PreAuthenticatedAuthenticationToken(userId, null,
                                List.of(new SimpleGrantedAuthority(role)));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        }
    }
