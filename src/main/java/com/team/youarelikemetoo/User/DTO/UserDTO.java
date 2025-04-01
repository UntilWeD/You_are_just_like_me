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
    private String oauth_id;
    private String role;

    public UserEntity toEntity(){
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setProvider(provider);
        user.setOauth_id(oauth_id);
        user.setRole(role);
        return user;
    }

    static public UserDTO fromEntity(UserEntity user){
        return UserDTO.builder()
                .oauth_id(user.getOauth_id())
                .provider(user.getProvider())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }

}
