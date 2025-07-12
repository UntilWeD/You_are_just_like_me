package com.team.youarelikemetoo.alarm.repository.mybatis;

import com.team.youarelikemetoo.alarm.dto.AlarmMessageDTO;
import com.team.youarelikemetoo.alarm.entity.TimeLabel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface AlarmMessageDTOMapper {

    Optional<AlarmMessageDTO> findRandomMessageByUserId(@Param("userId") Long userId, @Param("timeLabel") TimeLabel timeLabel);

}
