package com.team.youarelikemetoo.alarmFeed.service;

import com.team.youarelikemetoo.alarm.repository.AlarmJPARepository;
import com.team.youarelikemetoo.alarmFeed.dto.AlarmFeedDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AlarmFeedService {

    private AlarmJPARepository alarmJPARepository;

    public long saveAlarmFeed(AlarmFeedDTO dto, Long userId) {


    }
}
