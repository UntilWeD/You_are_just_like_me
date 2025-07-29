package com.team.youarelikemetoo.user.repository.mybatis;

import com.team.youarelikemetoo.user.dto.UserSimpleProfile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserSimpleProfileMapper {
    List<UserSimpleProfile> findFollowerAsUserSimpleProfileByFollowerId(@Param("followerId")Long followerId);
    List<UserSimpleProfile> getFollowerRecommendation(@Param("followerId") Long followerId);

}
