package com.team.youarelikemetoo.alarm.repository.mybatis;

import com.team.youarelikemetoo.alarm.dto.AlarmMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MyBatisAlarmMessageRepository {

    private final AlarmMessageDTOMapper mapper;

    public Optional<AlarmMessageDTO> findRandomMessageByUserId(Long userId){
        return mapper.findRandomMessageByUserId(userId);
    }


}
