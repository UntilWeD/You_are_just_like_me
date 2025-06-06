package com.team.youarelikemetoo.user.dto;

import com.team.youarelikemetoo.user.entity.Gender;
import com.team.youarelikemetoo.user.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDTO {

    private String name;
    private String oauthProvider;
    private String oauthId;
    private String role;
    private int age;
    private Gender gender;
    private String job;
    private String addr;



    public UserEntity toEntity(){
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setOauthProvider(oauthProvider);
        user.setOauthId(oauthId);
        user.setRole(role);
        user.setAge(age);
        user.setGender(gender);
        user.setJob(job);
        user.setAddr(addr);

        return user;
    }

    static public UserDTO fromEntity(UserEntity user){
        return UserDTO.builder()
                .oauthId(user.getOauthId())
                .oauthProvider(user.getOauthProvider())
                .name(user.getName())
                .role(user.getRole())
                .age(user.getAge())
                .gender(user.getGender())
                .job(user.getJob())
                .addr(user.getAddr())
                .build();
    }

}
