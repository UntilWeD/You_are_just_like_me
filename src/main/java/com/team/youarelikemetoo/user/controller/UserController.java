package com.team.youarelikemetoo.user.controller;


import com.team.youarelikemetoo.auth.dto.CustomOAuth2User;
import com.team.youarelikemetoo.user.dto.UserDTO;
import com.team.youarelikemetoo.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "사용자 API")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저의 정보를 조회합니다.", description = "사용자의 OAuth2아이디를 기반으로 사용자정보를 조회하여 반환합니다.")
    @ApiResponse(
            responseCode = "200", description = "유저 정보 조회 성공",
                content = @Content(schema = @Schema(implementation = UserDTO.class))
    )
    @GetMapping("/users/current")
    public ResponseEntity<?> getCurrentUserInfo(@AuthenticationPrincipal CustomOAuth2User user){
        String oauthId = user.getName();
        return userService.getUserInfo(oauthId);
    }


}
