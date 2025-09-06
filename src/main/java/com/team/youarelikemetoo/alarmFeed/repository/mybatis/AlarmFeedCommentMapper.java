package com.team.youarelikemetoo.alarmFeed.repository.mybatis;

import com.team.youarelikemetoo.alarmFeed.dto.AlarmFeedCommentDTO;
import com.team.youarelikemetoo.alarmFeed.dto.AlarmFeedDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AlarmFeedCommentMapper {

    List<AlarmFeedCommentDTO> findAllAlarmFeedCommentsByAlarmFeedId(@Param("alarmFeedId") Long alarmFeedId);
    AlarmFeedCommentDTO findAlarmFeedCommentByAlarmFeedId(@Param("alarmFeedId") Long alarmFeedId);

}
