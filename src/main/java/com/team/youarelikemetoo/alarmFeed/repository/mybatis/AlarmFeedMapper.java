package com.team.youarelikemetoo.alarmFeed.repository.mybatis;


import com.team.youarelikemetoo.alarmFeed.dto.AlarmFeedDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AlarmFeedMapper {

    List<AlarmFeedDTO> findAlarmFeedsByDayOfWeek(@Param("dayOfWeek") List<Integer> dayOfWeek);
    List<AlarmFeedDTO> findAlarmFeedsByDayOfWeekAndUserId(@Param("dayOfWeek") List<Integer> dayOfWeek, @Param("userId") Long userId);

}
