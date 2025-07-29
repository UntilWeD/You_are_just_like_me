package com.team.youarelikemetoo.user.controller;

import com.team.youarelikemetoo.alarmFeed.service.FollowService;
import com.team.youarelikemetoo.auth.dto.CustomUserDetails;
import com.team.youarelikemetoo.global.util.ApiResponse;
import com.team.youarelikemetoo.user.dto.UserDTO;
import com.team.youarelikemetoo.user.dto.UserSimpleProfile;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Operation(summary = "사용자가 팔로우한 사람들의 리스트를 조회합니다.",
            description = "현재 사용자의 id를 기반으로 팔로우 테이블에서 follower_id와 같은 행들을 조회하여 20개씩 묶어서 반환합니다.")
    @GetMapping("/followings")
    public ResponseEntity<?> getFollowingUsers(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        List<UserSimpleProfile> followingUsers = followService.getFollowingUsers(customUserDetails.getUserId());

        return ResponseEntity.ok(ApiResponse.success(followingUsers));
    }

    @Operation(summary = "사용자의 id를 기반으로 팔로잉 친구를 추천합니다.",
    description = "'나를 팔로우 하는 사람이'이 팔로우 하는사람을 추천한다. 트위터와 깃헙의 방식과 유사하며 친구의 친구를 팔로우 한다. ")
    @GetMapping("/followings/recommend")
    public ResponseEntity<?> getFollowingRecommendation(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        List<UserSimpleProfile> followingRecommendations = followService.getFollowingRecommendation(customUserDetails.getUserId());

        return ResponseEntity.ok(ApiResponse.success(followingRecommendations));
    }

}
