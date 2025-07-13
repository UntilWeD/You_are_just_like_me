package com.team.youarelikemetoo.alarm.repository.mybatis;

import com.team.youarelikemetoo.alarm.dto.AlarmMessageDTO;
import com.team.youarelikemetoo.alarm.entity.AlarmMessageTemplate;
import com.team.youarelikemetoo.alarm.entity.TimeLabel;
import com.team.youarelikemetoo.user.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AlarmMessageDTOMapper {
    List<AlarmMessageDTO> findRandomMessageByUserId(@Param("userId") Long userId, @Param("timeLabel") TimeLabel timeLabel);
    List<AlarmMessageDTO> findRandomUserByUserId(@Param("userId") Long userId, @Param("timeLabel") TimeLabel timeLabel);
    List<String> findRandomAlarmMessageTemplate(@Param("categoryId")Long categoryId,@Param("timeLabel") TimeLabel timeLabel);
}
