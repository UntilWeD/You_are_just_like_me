package com.team.youarelikemetoo.alarmFeed.repository;

import com.team.youarelikemetoo.alarmFeed.entity.AlarmFeedShare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlarmFeedShareJpaRepository extends JpaRepository<AlarmFeedShare, Long> {


    Optional<AlarmFeedShare> findAlarmFeedShareByAlarmFeed_IdAndUser_Id(Long alarmFeedId, Long userId);
}
