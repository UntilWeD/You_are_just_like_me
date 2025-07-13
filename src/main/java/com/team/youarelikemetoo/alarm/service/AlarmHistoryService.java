package com.team.youarelikemetoo.alarm.service;

import com.team.youarelikemetoo.alarm.dto.AlarmMessageDTO;
import com.team.youarelikemetoo.alarm.entity.Alarm;
import com.team.youarelikemetoo.alarm.entity.AlarmInstance;
import com.team.youarelikemetoo.alarm.repository.AlarmInstanceJpaRepository;
import com.team.youarelikemetoo.alarm.repository.AlarmJPARepository;
import com.team.youarelikemetoo.user.entity.UserEntity;
import com.team.youarelikemetoo.user.repository.UserJPARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmHistoryService {

    private final AlarmInstanceJpaRepository alarmInstanceJpaRepository;
    private final UserJPARepository userJPARepository;
    private final AlarmJPARepository alarmJPARepository;

    @Async
    public void saveAlarmInstanceAsync(Long userId, List<AlarmMessageDTO> dtos, List<String> alarmMessages){
        try{
            UserEntity user = userJPARepository.getReferenceById(userId);
            for(int i=0; i < dtos.size(); i++) {
                Long sourceAlarmId = dtos.get(i).getSourceAlarmId();
                Alarm alarm = alarmJPARepository.getReferenceById(sourceAlarmId);


                AlarmInstance alarmInstance = AlarmInstance.builder()
                        .renderedMessage(alarmMessages.get(i))
                        .createdAt(LocalDateTime.now())
                        .sourceAlarm(alarm)
                        .targetUser(user)
                        .build();

                alarmInstanceJpaRepository.save(alarmInstance);
            }
        } catch (Exception e){
            log.error("알람 인스턴스 저장 실패 : userId={} ", userId);
        }
    }


}
