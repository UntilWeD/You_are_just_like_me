package com.team.youarelikemetoo.user.controller;


import com.team.youarelikemetoo.auth.dto.CustomUserDetails;
import com.team.youarelikemetoo.global.util.ApiResponse;
import com.team.youarelikemetoo.user.dto.UserDTO;
import com.team.youarelikemetoo.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "User", description = "사용자 API")
@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // url 고쳐야함
    @Operation(summary = "유저의 정보를 조회합니다.", description = "사용자의 OAuth2아이디를 기반으로 사용자정보를 조회하여 반환합니다.")
    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUserInfo(@AuthenticationPrincipal CustomUserDetails user){
        String oauthId = user.getOauthId();
        return userService.getUserInfo(oauthId);
    }

    // url 고쳐야함 {userId}
    @PostMapping("/save")
    @Operation(summary = "유저 정보를 저장합니다.", description = "OAuth2 로그인 후 나머지 부가 정보를 저장합니다.")
    public ResponseEntity<?> getSaveUserRequest(@RequestPart(value = "data") UserDTO dto,
                                                @RequestPart(value = "image", required = false) MultipartFile imageFile,
                                                @AuthenticationPrincipal CustomUserDetails customUserDetails){
        UserDTO savedUserDTO = userService.saveUser(dto, imageFile, customUserDetails.getOauthId());

        return ResponseEntity.ok(ApiResponse.success(savedUserDTO));
    }

}
