    package io.studyit.userservice.jwt;

    import io.studyit.userservice.user.security.UserDetailsImpl;
    import jakarta.servlet.FilterChain;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
    import org.springframework.web.filter.OncePerRequestFilter;

    import java.io.IOException;
    import java.util.Collections;
    import java.util.List;

    @Slf4j
    public class HeaderAuthenticationFilter extends OncePerRequestFilter {

        @Override
        protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
            String path = request.getServletPath();
            String method = request.getMethod();

            if ("/user".equals(path) && "POST".equalsIgnoreCase(method)) {
                return true;
            }

            return false;
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            String userId = request.getHeader("X-User-Id");

            if (userId != null) {
                List<GrantedAuthority> authorities = Collections.emptyList();
                PreAuthenticatedAuthenticationToken authentication =
                        new PreAuthenticatedAuthenticationToken(
                                new UserDetailsImpl(userId, authorities), null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        }
    }
