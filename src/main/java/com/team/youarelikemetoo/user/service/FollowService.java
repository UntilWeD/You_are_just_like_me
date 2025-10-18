package com.team.youarelikemetoo.user.service;

import com.team.youarelikemetoo.user.dto.UserSimpleProfile;
import com.team.youarelikemetoo.user.repository.FollowJpaRepository;
import com.team.youarelikemetoo.user.entity.Follow;
import com.team.youarelikemetoo.user.repository.mybatis.MyBatisFollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowJpaRepository followJpaRepository;
    private final MyBatisFollowRepository myBatisFollowRepository;

    @Transactional
    public boolean follow(Long followerId, Long followingId) {
        if(followerId.equals(followingId))
            throw new RuntimeException("자기 자신은 팔로우할 수 없습니다.");
        boolean result = true;
        Optional<Follow> existedFollow = followJpaRepository.findByFollowerIdAndFollowingId(followerId, followingId);

        if(existedFollow.isEmpty()){
            followJpaRepository.save(Follow.builder()
                            .followerId(followerId)
                            .followingId(followingId)
                    .build());
        } else {
            result = false;
            followJpaRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);
        }

        return result;
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
