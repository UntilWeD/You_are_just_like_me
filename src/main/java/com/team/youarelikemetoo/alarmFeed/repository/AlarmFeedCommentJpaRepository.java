package com.team.youarelikemetoo.alarmFeed.repository;

import com.team.youarelikemetoo.alarmFeed.entity.AlarmFeedComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmFeedCommentJpaRepository extends JpaRepository<AlarmFeedComment, Long> {



}
