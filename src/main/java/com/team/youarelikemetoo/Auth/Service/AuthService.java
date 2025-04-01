package com.team.youarelikemetoo.Auth.Service;

import com.team.youarelikemetoo.Auth.DTO.KakaoUserResponse;
import com.team.youarelikemetoo.Auth.DTO.LoginRequest;
import com.team.youarelikemetoo.Auth.DTO.LoginResponse;
import com.team.youarelikemetoo.Global.JWT.JWTUtil;
import com.team.youarelikemetoo.User.DTO.UserDTO;
import com.team.youarelikemetoo.User.Entity.UserEntity;
import com.team.youarelikemetoo.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final WebClient webClient;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;


    public ResponseEntity<?> login(String accessToken, LoginRequest loginRequest){

        if (loginRequest.getAuthType().equals("kakao")) {
            KakaoUserResponse userInfo = getKakaoUserInfo(accessToken);

            if (userInfo == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰");
            }

            // 사용자 정보 생성 또는 조회
            UserEntity user = userRepository.findByOauth_idAndProvider(userInfo.getProviderId(), "kakao")
                    .orElseGet(() -> {
                        UserDTO authUser = UserDTO.builder()
                                .provider("kakao")
                                .name(userInfo.getName())
                                .oauth_id(userInfo.getProviderId())
                                .role("ROLE_USER")
                                .build();
                        return userRepository.save(authUser.toEntity());
                    });

            String token = jwtUtil.createJwt(user.getName(), user.getRole(), 60*60*60L);

            // 로그인 응답 생성
            LoginResponse loginResponse = LoginResponse.builder()
                    .token(token)
                    .user(UserDTO.fromEntity(user))
                    .build();

            return ResponseEntity.ok(loginResponse);
        }


        return new ResponseEntity<>(null);
    }

    private KakaoUserResponse getKakaoUserInfo(String accessToken){
        try{
            return webClient.get()
                    .uri("https://kapi.kakao.com/v2/user/me")
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(KakaoUserResponse.class)
                    .block();
        } catch (Exception e){
            return null;
        }
    }

}
