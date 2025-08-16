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
    private Integer age;
    private Gender gender;
    private String job;
    private String addr;
    private String instagramId;
    private String introduction;
    private String profileImageUrl;



    public UserEntity toEntity(){
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setOauthProvider(oauthProvider);
        user.setOauthId(oauthId);
        user.setRole(role);
        user.setInstagramId(instagramId);
        user.setIntroduction(introduction);

        // null-safe: null이거나 유효한 값만 set
        if (age != null && age > 0 && age < 150) {
            user.setAge(age);
        } else {
            user.setAge(null); // 명시적으로 null 처리
        }

        user.setGender(gender);
        user.setJob(job);
        user.setAddr(addr);

        return user;
    }

    // 유저 프로파일 이미지가 있을 시에 동적으로 변환되도록 해야함
    static public UserDTO fromEntity(UserEntity user){
        return UserDTO.builder()
                .oauthId(user.getOauthId())
                .oauthProvider(user.getOauthProvider())
                .name(user.getName())
                .role(user.getRole())
                .age(user.getAge() != null && user.getAge() >= 1 ? user.getAge() : null)
                .gender(user.getGender())
                .job(user.getJob())
                .addr(user.getAddr())
                .profileImageUrl(
                        user.getUserProfileImage() != null
                                ? user.getUserProfileImage().getImageUrl()
                                : null
                )
                .instagramId(user.getInstagramId())
                .introduction(user.getIntroduction())
                .build();
    }

}
