package com.team.youarelikemetoo.auth.service;

import com.team.youarelikemetoo.auth.dto.KakaoUserResponse;
import com.team.youarelikemetoo.auth.dto.LoginRequest;
import com.team.youarelikemetoo.auth.dto.LoginResponse;
import com.team.youarelikemetoo.auth.dto.ReissueResponse;
import com.team.youarelikemetoo.global.jwt.JWTUtil;
import com.team.youarelikemetoo.global.jwt.service.RedisService;
import com.team.youarelikemetoo.user.dto.UserDTO;
import com.team.youarelikemetoo.user.entity.UserEntity;
import com.team.youarelikemetoo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final WebClient webClient;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final RedisService redisService;


    public ResponseEntity<?> login(String accessToken, LoginRequest loginRequest){

        if (loginRequest.getAuthType().equals("kakao")) {
            KakaoUserResponse userInfo = getKakaoUserInfo(accessToken);

            if (userInfo == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰");
            }

            // 사용자 정보 생성 또는 조회
            UserEntity user = userRepository.findByOauthIdAndProvider(userInfo.getProviderId(), "kakao")
                    .orElseGet(() -> {
                        UserDTO authUser = UserDTO.builder()
                                .provider("kakao")
                                .name(userInfo.getName())
                                .oauthId(userInfo.getProviderId())
                                .role("ROLE_USER")
                                .build();
                        return userRepository.save(authUser.toEntity());
                    });

            String jwtAccessToken = jwtUtil.createJwt(userInfo.getProviderId(), user.getRole(), 60*60*60L*1000L);
            String jwtRefreshToken = jwtUtil.createJwt(userInfo.getProviderId(), user.getRole(), 24*60*60*60L);

            redisService.saveRefreshToken(user.getOauthId(), jwtRefreshToken, 24 * 60 * 60* 1000L);

            // 로그인 응답 생성
            LoginResponse loginResponse = LoginResponse.builder()
                    .accessToken(jwtAccessToken)
                    .refreshToken(jwtRefreshToken)
                    .user(UserDTO.fromEntity(user))
                    .build();

            return ResponseEntity.ok(loginResponse);
        }


        return new ResponseEntity<>(null);
    }

    public ResponseEntity<?> reissue(String refreshToken){

        String oauthId = jwtUtil.getOauthId(refreshToken);

        String storedRefreshToken = redisService.getRefreshToken(oauthId);
        if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh Token");
        }

        String role = jwtUtil.getRole(refreshToken);
        String newAccessToken = jwtUtil.createJwt(oauthId, role, 60*60*1000L);
        String jwtRefreshToken = jwtUtil.createJwt(oauthId, role, 24*60*60*60L);

        ReissueResponse response = new ReissueResponse(newAccessToken, jwtRefreshToken);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> logout(String oauthId, String accessToken){
        redisService.deleteRefreshToken(oauthId);

        // 무상태인 AccessToken에 대하여 블랙리스트에 담는 로직 후에 추가....
        long expiration = jwtUtil.getExpiration(accessToken) - System.currentTimeMillis();

        redisService.blacklistToken(accessToken, expiration);

        return ResponseEntity.ok("로그아웃 성공");
    }

    private KakaoUserResponse getKakaoUserInfo(String accessToken){
        log.info("카카오톡 로그인 전송 시작");
        log.info("accessToken ={}", accessToken);
        try{
            return webClient.get()
                    .uri("https://kapi.kakao.com/v2/user/me")
                    .header("Authorization", accessToken)

                    .retrieve()
                    .bodyToMono(KakaoUserResponse.class)
                    .block();
        } catch (Exception e){
            log.info("에러발생 : " + e);
            return null;
        }
    }

}
