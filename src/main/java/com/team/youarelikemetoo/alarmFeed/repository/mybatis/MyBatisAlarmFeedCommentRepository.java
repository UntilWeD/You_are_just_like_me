package com.team.youarelikemetoo.alarmFeed.repository.mybatis;

import com.team.youarelikemetoo.alarmFeed.dto.AlarmFeedCommentDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyBatisAlarmFeedCommentRepository {

    private final AlarmFeedCommentMapper mapper;

    public List<AlarmFeedCommentDTO> findAllAlarmFeedCommentsByAlarmFeedId(@Param("alarmFeedId") Long alarmFeedId){
        return mapper.findAllAlarmFeedCommentsByAlarmFeedId(alarmFeedId);
    }

    public AlarmFeedCommentDTO findAlarmFeedCommentByAlarmFeedId(@Param("alarmFeedId") Long alarmFeedId){
        return mapper.findAlarmFeedCommentByAlarmFeedId(alarmFeedId);
    }


}
