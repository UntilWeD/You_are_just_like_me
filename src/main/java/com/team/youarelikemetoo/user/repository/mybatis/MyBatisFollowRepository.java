package com.team.youarelikemetoo.user.repository.mybatis;

import com.team.youarelikemetoo.user.dto.UserSimpleProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyBatisFollowRepository {

    private final UserSimpleProfileMapper mapper;

    public List<UserSimpleProfile> findFollowerAsUserSimpleProfileByFollowerId(Long followerId){
        return mapper.findFollowerAsUserSimpleProfileByFollowerId(followerId);
    }

    public List<UserSimpleProfile> getFollowerRecommendation(Long followerId){
        return mapper.getFollowerRecommendation(followerId);
    }

}
