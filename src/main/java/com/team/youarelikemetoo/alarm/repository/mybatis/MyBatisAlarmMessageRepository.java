package com.team.youarelikemetoo.alarm.repository.mybatis;

import com.team.youarelikemetoo.alarm.dto.AlarmMessageDTO;
import com.team.youarelikemetoo.alarm.entity.AlarmMessageTemplate;
import com.team.youarelikemetoo.alarm.entity.TimeLabel;
import com.team.youarelikemetoo.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyBatisAlarmMessageRepository {

    private final AlarmMessageDTOMapper mapper;

    public List<AlarmMessageDTO> findRandomMessageByUserId(Long userId, TimeLabel timeLabel){
        return mapper.findRandomMessageByUserId(userId, timeLabel);
    }
    public List<AlarmMessageDTO> findRandomUserByUserId(Long userId, TimeLabel timeLabel){
        return mapper.findRandomUserByUserId(userId, timeLabel);
    }

    public List<String> findRandomAlarmMessageTemplate(Long categoryId, TimeLabel timeLabel){
        return mapper.findRandomAlarmMessageTemplate(categoryId, timeLabel);
    }

}
