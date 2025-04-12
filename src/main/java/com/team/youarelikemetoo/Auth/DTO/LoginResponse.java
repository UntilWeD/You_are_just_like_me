package com.team.youarelikemetoo.Auth.DTO;


import com.team.youarelikemetoo.User.DTO.UserDTO;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

    private String accessToken;
    private String refreshToken;
    private UserDTO user;
}
