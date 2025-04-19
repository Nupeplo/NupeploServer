package SpaceServer.com.SpaceServer.security.jwt;

import SpaceServer.com.SpaceServer.security.config.JwtProperties;
import SpaceServer.com.SpaceServer.member.service.security.CustomUserDetailsService;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;
    private final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24; // 24 시간
    private final long REFRESH_TOKEN_EXPIRATION = ACCESS_TOKEN_EXPIRATION * 7; // 7일
    public static final String ACCESS_TOKEN_HEADER = "AuthToken";  // 헤더 이름 상수
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * 애플리케이션 시작 시 비밀 키 로깅
     */
    @PostConstruct
    protected void init() {
        log.info("초기화된 비밀 키: {}", jwtProperties.getSecret()); // 올바르게 값 출력
    }

    /**
     * 액세스 토큰 생성
     */
    public String generateAccessToken(String userId) {
        Claims claims = Jwts.claims().setSubject(userId);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                .compact();
    }

    /**
     * 리프레시 토큰 생성
     */
    public String generateRefreshToken(String userId) {
        Claims claims = Jwts.claims().setSubject(userId);
        log.info("userId값  = `{}`", userId);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                .compact();

    }

    /**
     * 토큰에서 userId(subject) 추출
     */
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSecret())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    /**
     * 리프레시 토큰의 만료 일자 반환
     */
    public Date getRefreshTokenExpiryDate() {
        return new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION);
    }

    /**
     * 토큰을 통해 인증 객체(Authentication) 생성
     */
    public Authentication getAuthentication(String token) {
        // JWT Claims 추출
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSecret())
                .build()
                .parseClaimsJws(token)
                .getBody();

        // 권한 정보 추출 및 변환
        List<GrantedAuthority> authorities = extractAuthorities(claims);
        String userId = claims.getSubject();
        UserDetails securityUser = customUserDetailsService.loadUserByUsername(userId);

        return new UsernamePasswordAuthenticationToken(securityUser, null, authorities);
    }
    /**
     * HTTP 요청 헤더에서 토큰 추출..
     */
    public String getHeaderToken(HttpServletRequest request, String tokenHeader) {
        String token = request.getHeader(tokenHeader);

        if (token == null || token.isBlank()) {
            log.error("{} 헤더가 요청에 포함되지 않았습니다.", tokenHeader);
            throw new RuntimeException("잘못된 토큰 입니다.");
        }

//        log.info("수신한 토큰: {}", token);
        return token.trim(); // 순수 토큰 반환
    }

    /**
     * Claims에서 역할(권한) 정보를 추출
     */
    private List<GrantedAuthority> extractAuthorities(Claims claims) {
        return Optional.ofNullable(claims.get("roles"))
                .map(auth -> Arrays.stream(auth.toString().split(","))
                        .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role.trim()))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());


    }

    /**
     * 토큰 유효성 검증
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtProperties.getSecret())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            throw new RuntimeException("TOKEN_MISMATCH");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            throw new RuntimeException("EXPIRED_TOKEN");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            throw new RuntimeException("INVALID_TOKEN");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            throw new RuntimeException("TOKEN_NOT_FOUND");
        }
    }
}
