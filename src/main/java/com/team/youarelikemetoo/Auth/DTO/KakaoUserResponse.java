package com.team.youarelikemetoo.Auth.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public class KakaoUserResponse implements OAuth2Response{

    @JsonProperty("id")
    private String id;

    @JsonProperty("properties")
    private Map<String, String> properties; // 닉네임을 포함하는 properties 필드


    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return id;
    }

    // 일단 만들어 놓기
    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getName() {
        return properties != null ? properties.get("nickname") : null;
    }
}
