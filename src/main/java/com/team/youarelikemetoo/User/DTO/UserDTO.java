package com.team.youarelikemetoo.User.DTO;

import com.team.youarelikemetoo.User.Entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDTO {

    private String name;
    private String provider;
    private String oauthId;
    private String role;

    public UserEntity toEntity(){
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setProvider(provider);
        user.setOauthId(oauthId);
        user.setRole(role);
        return user;
    }

    static public UserDTO fromEntity(UserEntity user){
        return UserDTO.builder()
                .oauthId(user.getOauthId())
                .provider(user.getProvider())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }

}
