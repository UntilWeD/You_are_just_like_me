package com.team.youarelikemetoo.user.controller;

import com.team.youarelikemetoo.alarmFeed.service.FollowService;
import com.team.youarelikemetoo.auth.dto.CustomUserDetails;
import com.team.youarelikemetoo.global.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @Operation(summary = "다른 사용자를 팔로우 합니다.", description = "파라미터에 팔로잉할 유저의 id를 받아와 요청을 보내는 유저의 id와 함께 저장합니다.")
    @PostMapping("/follow/{followingId}")
    public ResponseEntity<?> getFollowRequest(@PathVariable Long followingId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails){
        followService.follow(customUserDetails.getUserId(), followingId);

        return ResponseEntity.ok(ApiResponse.success("팔로우가 성공하였습니다."));
    }


    @Operation(summary = "다른 사용자를 언 팔로우 합니다.", description = "파라미터에 언 팔로잉할 유저의 id를 받아와 요청을 보내는 유저의 id로 삭제합니다.")
    @PostMapping ("/unfollow/{followingId}")
    public ResponseEntity<?> getUnfollowRequest(@PathVariable Long followingId,
                                              @AuthenticationPrincipal CustomUserDetails customUserDetails){
        followService.
                unfollow(customUserDetails.getUserId(), followingId);

        return ResponseEntity.ok(ApiResponse.success("언 팔로우가 성공하였습니다."));
    }

}
