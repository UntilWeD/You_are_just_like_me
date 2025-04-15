package com.team.youarelikemetoo.auth.dto;


import com.team.youarelikemetoo.user.dto.UserDTO;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

    private String accessToken;
    private String refreshToken;
    private UserDTO user;
}
