package com.team.youarelikemetoo.alarmFeed.repository;

import com.team.youarelikemetoo.alarmFeed.entity.AlarmFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmFeedJPARepository extends JpaRepository<AlarmFeed, Long> {


}
