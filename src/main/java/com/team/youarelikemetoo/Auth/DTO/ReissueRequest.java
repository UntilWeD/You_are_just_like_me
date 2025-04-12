package com.team.youarelikemetoo.Auth.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReissueRequest {

    private String accessToken;
    private String refreshToken;

}
