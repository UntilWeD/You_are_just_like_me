package com.team.youarelikemetoo.alarmFeed.repository;

import com.team.youarelikemetoo.alarmFeed.entity.AlarmFeedLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlarmFeedLikeJpaRepository extends JpaRepository<AlarmFeedLike, Long> {

    public Optional<AlarmFeedLike> findAlarmFeedLikeByAlarmFeed_IdAndUser_Id(Long alarmFeedId, Long userId);

}
