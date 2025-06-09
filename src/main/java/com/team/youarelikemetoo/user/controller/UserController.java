package com.team.youarelikemetoo.user.controller;


import com.team.youarelikemetoo.auth.dto.CustomUserDetails;
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
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "사용자 API")
@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저의 정보를 조회합니다.", description = "사용자의 OAuth2아이디를 기반으로 사용자정보를 조회하여 반환합니다.")
    @ApiResponse(
            responseCode = "200", description = "유저 정보 조회 성공",
                content = @Content(schema = @Schema(implementation = UserDTO.class))
    )
    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUserInfo(@AuthenticationPrincipal CustomUserDetails user){
        String oauthId = user.getOauthId();
        return userService.getUserInfo(oauthId);
    }

    @PostMapping("/save")
    @Operation(summary = "유저 정보를 저장합니다.", description = "OAuth2 로그인 후 나머지 부가 정보를 저장합니다.")
    @ApiResponse(
            responseCode = "200", description = "유저 정보 저장",
                content = @Content(schema = @Schema(implementation = UserDTO.class))
    )
    public ResponseEntity<?> getSaveUserRequest(@RequestBody UserDTO userDTO,
                                                @AuthenticationPrincipal CustomUserDetails user){
        String oauthId = user.getOauthId();
        return userService.saveUser(userDTO, oauthId);
    }



}
