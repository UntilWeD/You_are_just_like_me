package com.team.youarelikemetoo.alarm.repository.mybatis;

import com.team.youarelikemetoo.alarm.dto.AlarmMessageDTO;
import com.team.youarelikemetoo.alarm.entity.TimeLabel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyBatisAlarmMessageRepository {

    private final AlarmMessageDTOMapper mapper;

    public List<AlarmMessageDTO> findRandomMessageByUserId(Long userId, TimeLabel timeLabel){
        return mapper.findRandomMessageByUserId(userId, timeLabel);
    }


}
