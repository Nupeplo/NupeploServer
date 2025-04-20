package SpaceServer.com.SpaceServer.security.config;

import SpaceServer.com.SpaceServer.security.jwt.JwtFilter;
import SpaceServer.com.SpaceServer.security.jwt.JwtTokenProvider;
import SpaceServer.com.SpaceServer.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtUtil jwtUtil;

    private final String[] WHITE_LIST = {
            "/" ,
            "/api/v1/auth/login",
            "/api/v1/auth/stars/**",
            "/api/v1/auth//**",
            "/swagger-ui/**",
            "/api/v1/planet/**",
            "/v3/api-docs/**",
            "/app/file/**",
            "/api/v1/auth/login/simple"
    };

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(getWhiteListMatchers()).permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(new JwtFilter(jwtTokenProvider ,jwtUtil),
                        UsernamePasswordAuthenticationFilter.class)
                .cors(Customizer.withDefaults());
        return http.build();
    }

    private RequestMatcher[] getWhiteListMatchers() {
        return Arrays.stream(WHITE_LIST)
                .map(AntPathRequestMatcher::new)
                .toArray(RequestMatcher[]::new);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // ✅ 허용할 Origin 명시 (프론트 배포 주소 포함)
        config.setAllowedOrigins(List.of(
                "https://nupeplo-front.vercel.app",
                "https://spacex.n-e.kr"
        ));

        // ✅ 허용할 HTTP Method
        config.setAllowedMethods(List.of("DELETE", "GET", "POST", "PUT", "OPTIONS"));

        // ✅ 모든 헤더 허용 (Authorization 등 포함)
        config.setAllowedHeaders(List.of("*"));

        // ✅ 인증 정보 허용 (토큰, 쿠키 등)
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // 전체 경로에 적용

        return source;
    }
}
