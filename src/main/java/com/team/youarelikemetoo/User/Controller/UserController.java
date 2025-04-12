package com.team.youarelikemetoo.User.Controller;


import com.team.youarelikemetoo.Auth.DTO.CustomOAuth2User;
import com.team.youarelikemetoo.User.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/current")
    public ResponseEntity<?> getCurrentUserInfo(@AuthenticationPrincipal CustomOAuth2User user){
        String oauthId = user.getName();
        return userService.getUserInfo(oauthId);
    }


}
