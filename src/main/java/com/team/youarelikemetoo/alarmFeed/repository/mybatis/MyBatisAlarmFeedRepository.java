package com.team.youarelikemetoo.alarmFeed.repository.mybatis;

import com.team.youarelikemetoo.alarmFeed.dto.AlarmFeedDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyBatisAlarmFeedRepository {

    private final AlarmFeedMapper mapper;

    public List<AlarmFeedDTO> findAlarmFeedsByDayOfWeek(List<Integer> dayOfWeek){
        return mapper.findAlarmFeedsByDayOfWeek(dayOfWeek);
    }

    public List<AlarmFeedDTO> findAlarmFeedsByDayOfWeekAndUserId(List<Integer> dayOfWeek, Long userId){
        return mapper.findAlarmFeedsByDayOfWeekAndUserId(dayOfWeek, userId);
    }


}
