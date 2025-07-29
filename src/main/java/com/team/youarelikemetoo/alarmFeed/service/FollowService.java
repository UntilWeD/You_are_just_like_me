package com.team.youarelikemetoo.alarmFeed.service;

import com.team.youarelikemetoo.user.dto.UserDTO;
import com.team.youarelikemetoo.user.dto.UserSimpleProfile;
import com.team.youarelikemetoo.user.repository.FollowJpaRepository;
import com.team.youarelikemetoo.user.entity.Follow;
import com.team.youarelikemetoo.user.repository.UserJPARepository;
import com.team.youarelikemetoo.user.repository.mybatis.MyBatisFollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowJpaRepository followJpaRepository;
    private final MyBatisFollowRepository myBatisFollowRepository;

    @Transactional
    public void follow(Long followerId, Long followingId) {
        if(followerId.equals(followingId))
            throw new RuntimeException("자기 자신은 팔로우할 수 없습니다.");

        if(!followJpaRepository.existsByFollowerIdAndFollowingId(followerId, followingId)){
            followJpaRepository.save(Follow.builder()
                            .followerId(followerId)
                            .followingId(followingId)
                    .build());
        }
    }

    @Transactional
    public void unfollow(Long followerId, Long unfollowingId) {
        followJpaRepository.deleteByFollowerIdAndFollowingId(followerId, unfollowingId);
    }

    public List<UserSimpleProfile> getFollowingUsers(Long userId) {
        List<UserSimpleProfile> profiles = myBatisFollowRepository.findFollowerAsUserSimpleProfileByFollowerId(userId);
        return profiles;
    }

    public List<UserSimpleProfile> getFollowingRecommendation(Long followerId) {
        List<UserSimpleProfile> profiles = myBatisFollowRepository.getFollowerRecommendation(followerId);
        return profiles;
    }
}
