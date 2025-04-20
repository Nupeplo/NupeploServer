package SpaceServer.com.SpaceServer.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtUtil jwtUtil;
    /**
     * JWT 필터가 적용되지 않아야 하는 경로들 정의
     */
    private boolean isExemptPath(HttpServletRequest request) {
        String path = request.getRequestURI();

        // 예외 처리할 경로들
        return path.equals("/")  // ✅ 홈 화면 추가
                || path.equals("/api/v1/auth/login")
                || path.equals("/api/v1/planet")
                || path.startsWith("/api/v1/auth")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs");
    }
    /**
     * 실제 요청 필터링 로직
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 인증이 필요 없는 경로일 경우 바로 통과
        if (isExemptPath(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 요청 중복 처리 방지 (한 요청에 대해 이 필터가 여러 번 실행되는 경우 방지)
        if (request.getAttribute("JwtFilterProcessed") != null) {
            log.warn("JwtAuthenticationFilter: 이미 처리된 요청입니다.");
            filterChain.doFilter(request, response);
            return;
        }
        request.setAttribute("JwtFilterProcessed", true);

        try {
            // 헤더에서 Access Token 추출
            String accessToken = jwtTokenProvider.getHeaderToken(request, JwtTokenProvider.ACCESS_TOKEN_HEADER);

            // Access Token 이 존재할 경우
            if (accessToken != null) {
                try {
                    if (jwtTokenProvider.validateToken(accessToken)) {
                        // 인증 객체 설정
                        setAuthentication(accessToken);
//                        log.info("AccessToken 인증 성공: {}", accessToken);
                    }
                } catch (ExpiredJwtException e) {
                    // Access Token 만료
                    log.warn("Access Token expired: {}", e.getMessage());
                    handleExpiredAccessToken(response); // 클라이언트에 만료 알림
                    return;
                }
            } else {
                // 토큰이 없는 경우
                log.info("Access Token is none. Refresh Token check start");
                handleExpiredAccessToken(response); // 클라이언트에 만료 알림
                return;
            }

            // 정상적인 경우 다음 필터로 전달
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            // 필터 처리 중 예외 발생 시 에러 응답 처리
            log.error("Unhandled exception in JwtFilter: {}", e.getMessage(), e);
            if (!response.isCommitted()) {
                jwtUtil.jwtExceptionHandler(response, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * 토큰이 만료되었을 때 클라이언트에 에러 응답 전송
     */
    private void handleExpiredAccessToken(HttpServletResponse response) {
        log.error("Access Token 만료 - 로그인 필요");
        jwtUtil.jwtExceptionHandler(response, "Access Token 만료. 다시 로그인 필요.", HttpStatus.UNAUTHORIZED);
    }

    /**
     * JWT 인증 정보를 SecurityContextHolder에 설정
     */
    private void setAuthentication(String token) {
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
