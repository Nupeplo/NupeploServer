package SpaceServer.com.SpaceServer.member.service;

import SpaceServer.com.SpaceServer.image.ProfileImageUtil;
import SpaceServer.com.SpaceServer.member.dto.SimpleLoginRequest;
import SpaceServer.com.SpaceServer.security.jwt.JwtTokenProvider;
import SpaceServer.com.SpaceServer.member.dto.KakaoUserDto;
import SpaceServer.com.SpaceServer.member.dto.TokenResponse;
import SpaceServer.com.SpaceServer.member.entity.UserEntity;
import SpaceServer.com.SpaceServer.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ProfileImageUtil profileImageUtil;

    // 카카오에서 가져오기
    public KakaoUserDto getUserProfile(String kakaoAccessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " +kakaoAccessToken);

        HttpEntity<?> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    "https://kapi.kakao.com/v2/user/me",
                    HttpMethod.GET,
                    request,
                    new ParameterizedTypeReference<>() {}
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();

                System.out.println("Kakao API 응답: " + responseBody);

                // 데이터 파싱
                String userId = String.valueOf(responseBody.get("id"));
                Map<String, Object> kakaoAccount = (Map<String, Object>) responseBody.get("kakao_account");
                String email = kakaoAccount != null ? (String) kakaoAccount.get("email") : null;
                Map<String, Object> profile = kakaoAccount != null ? (Map<String, Object>) kakaoAccount.get("profile") : null;
                String nickname = profile != null ? (String) profile.get("nickname") : null;
                String profileImage = profile != null ? (String) profile.get("profile_image_url") : null;

                // KakaoUserDto 객체 생성
                return KakaoUserDto.builder()
                        .userId(userId)
                        .nickname(nickname)
                        .build();
            } else {
                throw new RuntimeException("Kakao API 응답 실패: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch Kakao user profile: " + e.getMessage(), e);
        }
    }
    /**
     * 회원가입
     */
    public TokenResponse processLogin(KakaoUserDto kakaoUserDto) {

        // 중복된 아이디 확인하기
        boolean isUserExist = findUserId(kakaoUserDto.getUserId());
        if(isUserExist){
            throw new RuntimeException("이미 회원가입을 한 회원입니다.");
        }
        UserEntity userEntity = userRepository.findByUserId(kakaoUserDto.getUserId())
                .orElseGet(() -> {
                    String randomProfileUrl = profileImageUtil.getRandomProfileUrl();
                    UserEntity newUser = UserEntity.builder()
                            .userId(kakaoUserDto.getUserId())
                            .nickname(Optional.ofNullable(kakaoUserDto.getNickname()).orElse("카카오유저"))
                            .profileImageUrl(randomProfileUrl)
                            .build();
                    return userRepository.save(newUser);
                });

        //  JWT 생성
        String accessToken = jwtTokenProvider.generateAccessToken(userEntity.getUserId());
        String refreshToken = jwtTokenProvider.generateRefreshToken(userEntity.getUserId());
        return new TokenResponse(accessToken, refreshToken);
    }
    /**
     * 간편 회원가입
     */
//    @Transactional
//    public TokenResponse processSimpleLogin(SimpleLoginRequest request) {
//        // 이미 회원이면 가져오고
//        UserEntity user = userRepository.findByUserId(request.getUserId())
//                .orElseGet(() -> {
//                    // 신규 유저면 자동 회원가입
//                    String profileUrl = profileImageUtil.getRandomProfileUrl();
//                    return userRepository.save(UserEntity.builder()
//                            .userId(request.getUserId())
//                            .nickname(request.getNickname())
//                            .profileImageUrl(profileUrl)
//                            .build());
//                });
//
//        // JWT 발급
//        String accessToken = jwtTokenProvider.generateAccessToken(user.getUserId());
//        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getUserId());
//
//        return new TokenResponse(accessToken, refreshToken);
//    }
    /**
     * 로그인
     */
    public TokenResponse loginKakao(String kakaoAccessToken) {
        KakaoUserDto kakaoUserInfo = getUserProfile(kakaoAccessToken);
        log.info("kakaoUserInfo= {} " , kakaoUserInfo.getUserId());
        // 카카오 아이디 -> 사용자이메일 -> DB 조회
        UserEntity user = findByUserId(kakaoUserInfo.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        String accessToken = jwtTokenProvider.generateAccessToken(user.getUserId());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getUserId());
        Date expiresAt = jwtTokenProvider.getRefreshTokenExpiryDate();
        return new TokenResponse(accessToken, refreshToken);
    }

    /**
     * 사용자 찾기
     */
    public Optional<UserEntity> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    /**
     * 가입한 사용자 True or False
     */
    public boolean findUserId(String userId) {
        return userRepository.existsByUserId(userId);
    }
}
