package com.team.youarelikemetoo.auth.controller;

import com.team.youarelikemetoo.auth.dto.*;
import com.team.youarelikemetoo.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Tag(name = "OAuth2", description = "OAuth2 로그인에 관하여 다루는 Api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "OAuth2 로그인", description = "OAuth2 로그인 액세스 토큰과 LoginRequest로 해당 사용자의 리소스 정보를" +
            " 등록하고 JWT를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<?> getLoginRequest(@RequestHeader("Authorization") String accessToken,
                                             @RequestBody LoginRequest loginRequest){
        if (!"kakao".equals(loginRequest.getAuthType())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("지원하지 않는 로그인 타입");
        }
        return authService.login(accessToken, loginRequest);
    }

    @Operation(summary = "OAuth2 유저 JWT 토큰 재발급", description = "해당 요청의 JWT토큰으로부터 얻은 값으로 사용자의 새로운 " +
            "리프레시 토큰을 발급합니다.")
    @ApiResponse(
            responseCode = "200", description = "성공",
                content = @Content(schema = @Schema(implementation = ReissueResponse.class))
    )
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody ReissueRequest request){
        return authService.reissue(request.getAccessToken(),request.getRefreshToken());
    }

    @Operation(summary = "OAuth2 유저 로그아웃", description = "해당 사용자의 정보를 기반으로 액세스 토큰 블랙리스트 등록, " +
            "리프레시 토큰 삭제를 합니다.")
    @ApiResponse(
            responseCode = "200", description = "로그아웃 성공",
                content = @Content(schema = @Schema(implementation = String.class))
    )
    @PostMapping ("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal CustomOAuth2User user,
                                    @RequestHeader("Authorization") String authHeader){
        String accessToken = authHeader.replace("Bearer ", "");
        return authService.logout(user.getName(), accessToken);
    }


    

}
