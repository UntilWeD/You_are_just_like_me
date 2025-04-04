package com.team.youarelikemetoo.Auth.Controller;

import com.team.youarelikemetoo.Auth.DTO.LoginRequest;
import com.team.youarelikemetoo.Auth.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
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

}
