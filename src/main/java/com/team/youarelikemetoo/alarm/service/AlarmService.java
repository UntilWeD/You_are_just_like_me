package com.team.youarelikemetoo.alarm.service;

import com.team.youarelikemetoo.alarm.dto.AlarmDTO;
import com.team.youarelikemetoo.alarm.dto.AlarmSaveResponse;
import com.team.youarelikemetoo.alarm.entity.Alarm;
import com.team.youarelikemetoo.alarm.repository.AlarmJPARepository;
import com.team.youarelikemetoo.global.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmJPARepository alarmJPARepository;

    @Transactional
    public ResponseEntity<?> saveAlarm(AlarmDTO alarmDTO){
        log.info(alarmDTO.toString());
        Alarm savedAlarm = alarmJPARepository.save(alarmDTO.toEntity());
        AlarmSaveResponse response = new AlarmSaveResponse(savedAlarm.getId(), LocalDateTime.now());

        return ResponseEntity.ok(ApiResponse.success(response));
    }


    public ResponseEntity<?> getAlarm(Long alarmId) {
        Optional<Alarm> alarm = alarmJPARepository.findById(alarmId);
        if(alarm.isPresent()){
            return ResponseEntity.ok(ApiResponse.success(AlarmDTO.fromEntity(alarm.get())));
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.failure("[MSG] 존재하지 않는 알람입니다."));
        }

    }

    @Transactional
    public ResponseEntity<?> updateAlarm(Long alarmId, AlarmDTO alarmDTO){
        Optional<Alarm> alarm = alarmJPARepository.findById(alarmId);
        if(alarm.isPresent()){
            alarm.get().updateAlarmStatus(alarmDTO);
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("[MSG] 존재하지 않는 알람입니다.");
        }
        return ResponseEntity.ok(ApiResponse.success("[MSG] 알람이 성공적으로 수정되었습니다."));
    }

    public ResponseEntity<?> deleteAlarm(Long alarmId) {
        alarmJPARepository.deleteById(alarmId);
        return ResponseEntity.ok(ApiResponse.success("[MSG] 알람이 성공적으로 삭제되었습니다."));
    }

    private Long find


}
