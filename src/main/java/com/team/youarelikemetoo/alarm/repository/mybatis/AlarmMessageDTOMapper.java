package com.team.youarelikemetoo.alarm.repository.mybatis;

import com.team.youarelikemetoo.alarm.dto.AlarmMessageDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface AlarmMessageDTOMapper {

    Optional<AlarmMessageDTO> findRandomMessageByUserId(Long userId);

}
