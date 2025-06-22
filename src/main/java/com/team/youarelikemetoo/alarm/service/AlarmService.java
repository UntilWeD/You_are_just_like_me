package com.team.youarelikemetoo.alarm.service;

import com.team.youarelikemetoo.alarm.dto.AlarmDTO;
import com.team.youarelikemetoo.alarm.dto.AlarmMessageDTO;
import com.team.youarelikemetoo.alarm.entity.Alarm;
import com.team.youarelikemetoo.alarm.entity.Category;
import com.team.youarelikemetoo.alarm.repository.AlarmJPARepository;
import com.team.youarelikemetoo.alarm.repository.CategoryJPARepository;
import com.team.youarelikemetoo.alarm.repository.mybatis.MyBatisAlarmMessageRepository;
import com.team.youarelikemetoo.global.annotation.LogExecutionTime;
import com.team.youarelikemetoo.global.util.ApiResponse;
import com.team.youarelikemetoo.user.entity.UserEntity;
import com.team.youarelikemetoo.user.repository.UserJPARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AlarmService {

    private final AlarmJPARepository alarmJPARepository;
    private final UserJPARepository userJPARepository;
    private final CategoryJPARepository categoryJPARepository;
    private final MyBatisAlarmMessageRepository myBatisAlarmMessageRepository;
    private final AlarmHistoryService alarmHistoryService;

    public ResponseEntity<?> saveAlarm(AlarmDTO alarmDTO, Long userId){
        log.info(alarmDTO.toString());

        UserEntity user = userJPARepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = categoryJPARepository.findByCategoryName(alarmDTO.getCategory())
                .orElseThrow(() -> new RuntimeException("Category not found"));


        Alarm savedAlarm = alarmJPARepository.save(alarmDTO.toEntity(user, category));
        return ResponseEntity.ok(ApiResponse.success(savedAlarm.getId()));
    }


    @Transactional(readOnly = true)
    public ResponseEntity<?> getAlarm(Long alarmId) {
        Optional<Alarm> alarm = alarmJPARepository.findById(alarmId);
        if(alarm.isPresent()){

            AlarmDTO alarmDTO = AlarmDTO.fromEntity(alarm.get());
            return ResponseEntity.ok(ApiResponse.success(alarmDTO));
        } else{

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.failure("[MSG] 존재하지 않는 알람입니다."));
        }

    }


    public ResponseEntity<?> updateAlarm(Long alarmId, AlarmDTO alarmDTO){
        Optional<Alarm> alarm = alarmJPARepository.findById(alarmId);

        if(alarm.isPresent()){
            Category category = categoryJPARepository.findByCategoryName(alarmDTO.getCategory())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            alarm.get().updateAlarmStatus(alarmDTO, category);
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("[MSG] 존재하지 않는 알람입니다.");
        }
        return ResponseEntity.ok(ApiResponse.success("[MSG] 알람이 성공적으로 수정되었습니다."));
    }

    public ResponseEntity<?> deleteAlarm(Long alarmId) {
        alarmJPARepository.deleteById(alarmId);
        return ResponseEntity.ok(ApiResponse.success("[MSG] 알람이 성공적으로 삭제되었습니다."));
    }


    @LogExecutionTime
    public ResponseEntity<?> getAlarmMessage(Long userId) {
        AlarmMessageDTO dto = myBatisAlarmMessageRepository.findRandomMessageByUserId(userId)
                .orElseThrow(() -> new RuntimeException("AlarmMessage Not Found"));

        String alarmMessage = buildAlarmMessage(dto);

        // 성능을 위해 알람 인스턴스 저장은 비동기 처리
        // 또한 단일책임원칙을 위해 역할을 분리하기도 위함
        alarmHistoryService.saveAlarmInstanceAsync(userId, dto.getSourceAlarmId(),alarmMessage);

        return ResponseEntity.ok(ApiResponse.success(alarmMessage));
    }

    private String buildAlarmMessage(AlarmMessageDTO dto){
        String formattedTime = dto.getTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        return dto.getMessageTemplate()
                .replace("{username}", dto.getName())
                .replace("{time}", formattedTime);
    }
}
