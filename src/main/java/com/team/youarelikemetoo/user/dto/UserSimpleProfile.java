package com.team.youarelikemetoo.user.dto;

import com.team.youarelikemetoo.user.entity.UserEntity;
import lombok.*;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserSimpleProfile {

    private Long userId;
    private String name;
    private String imageUrl;

    public UserSimpleProfile fromEntity(UserEntity entity){
        UserSimpleProfile dto = UserSimpleProfile.builder()
                .userId(entity.getId())
                .name(entity.getName())
                .imageUrl(entity.getUserProfileImage().getImageUrl())
                .build();

        return dto;
    }
}
