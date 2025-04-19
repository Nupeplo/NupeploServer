package SpaceServer.com.SpaceServer.member;


import SpaceServer.com.SpaceServer.member.dto.KakaoUserDto;
import SpaceServer.com.SpaceServer.member.dto.TokenRequest;
import SpaceServer.com.SpaceServer.member.dto.TokenResponse;
import SpaceServer.com.SpaceServer.member.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequestMapping("/api/v1/auth")
@RestController
@AllArgsConstructor
@Tag(name = "Auth", description = "사용자 로그인")
public class UserController {

    private final UserService userService;

    /**
     * 카카오로 회원가입 및 로그인
     */
    @Operation(summary = "SNS 로그인/회원가입", description = "카카오 SNS 로그인 및 회원가입 통합 API")
    @PostMapping("/login")
    public ResponseEntity<?> authSNS(@RequestBody TokenRequest tokenRequest) {
        try {
            String accessToken = tokenRequest.getAccessToken();
            String refreshToken = tokenRequest.getRefreshToken(); // 카카오 리프레시 토큰 (선택)

            // 1. 카카오 사용자 정보 가져오기
            KakaoUserDto kakaoUser = userService.getUserProfile(accessToken);
            log.info("카카오 유저 정보 조회 완료: {}", kakaoUser.getUserId());

            boolean exists = userService.findUserId(kakaoUser.getUserId());
            TokenResponse tokenResponse;

            if (exists) {
                log.info("기존 회원입니다. 로그인 처리 진행");
                tokenResponse = userService.loginKakao(kakaoUser.getUserId());
            } else {
                log.info("신규 회원입니다. 회원가입 진행");
                tokenResponse = userService.processLogin(kakaoUser);
            }

            return ResponseEntity.ok(tokenResponse);

        } catch (Exception e) {
            log.error("서버 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("다시 시도해주세요.");
        }
    }
}