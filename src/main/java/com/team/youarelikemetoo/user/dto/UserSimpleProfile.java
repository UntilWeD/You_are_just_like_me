package com.team.youarelikemetoo.user.dto;

import com.team.youarelikemetoo.user.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserSimpleProfile {

    private Long id;
    private String name;
    private String profileImageUrl;

    public UserSimpleProfile fromEntity(UserEntity entity){
        UserSimpleProfile dto = UserSimpleProfile.builder()
                .id(entity.getId())
                .name(entity.getName())
                .profileImageUrl(entity.getUserProfileImage().getImageUrl())
                .build();

        return dto;
    }
}
