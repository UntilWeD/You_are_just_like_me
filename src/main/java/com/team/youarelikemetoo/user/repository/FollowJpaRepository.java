package com.team.youarelikemetoo.user.repository;

import com.team.youarelikemetoo.user.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FollowJpaRepository extends JpaRepository<Follow, Long> {
    public boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);

    public long deleteByFollowerIdAndFollowingId(Long followerId, Long followingId);
    public Optional<Follow> findByFollowerIdAndFollowingId(Long followerId, Long followingId);
}
