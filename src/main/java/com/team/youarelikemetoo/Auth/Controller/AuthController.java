package com.team.youarelikemetoo.Auth.Controller;

import com.team.youarelikemetoo.Auth.DTO.CustomOAuth2User;
import com.team.youarelikemetoo.Auth.DTO.LoginRequest;
import com.team.youarelikemetoo.Auth.DTO.ReissueRequest;
import com.team.youarelikemetoo.Auth.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> getLoginRequest(@RequestHeader("Authorization") String accessToken,
                                             @RequestBody LoginRequest loginRequest){
        if (!"kakao".equals(loginRequest.getAuthType())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("지원하지 않는 로그인 타입");
        }

        return authService.login(accessToken, loginRequest);
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody ReissueRequest request){
        return authService.reissue(request.getAccessToken(),request.getRefreshToken());
    }

    @PostMapping ("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal CustomOAuth2User user,
                                    @RequestHeader("Authorization") String authHeader){
        String accessToken = authHeader.replace("Bearer ", "");
        return authService.logout(user.getName(), accessToken);
    }


    

}
