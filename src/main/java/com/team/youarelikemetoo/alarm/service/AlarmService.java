package com.team.youarelikemetoo.alarm.service;

import com.team.youarelikemetoo.alarm.dto.AlarmDTO;
import com.team.youarelikemetoo.alarm.dto.AlarmMessageDTO;
import com.team.youarelikemetoo.alarm.entity.*;
import com.team.youarelikemetoo.alarm.repository.AlarmJPARepository;
import com.team.youarelikemetoo.alarm.repository.CategoryJPARepository;
import com.team.youarelikemetoo.alarm.repository.mybatis.MyBatisAlarmMessageRepository;
import com.team.youarelikemetoo.auth.dto.CustomUserDetails;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmJPARepository alarmJPARepository;
    private final UserJPARepository userJPARepository;
    private final CategoryJPARepository categoryJPARepository;
    private final MyBatisAlarmMessageRepository myBatisAlarmMessageRepository;
    private final AlarmHistoryService alarmHistoryService;

    @Transactional
    public ResponseEntity<?> saveAlarm(AlarmDTO alarmDTO, Long userId){
        log.info(alarmDTO.toString());

        UserEntity user = userJPARepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = categoryJPARepository.findByCategoryName(alarmDTO.getCategory())
                .orElseThrow(() -> new RuntimeException("Category not found"));


        Alarm savedAlarm = alarmDTO.toEntity(user, category);

        List<AlarmDay> alarmDays = alarmDTO.getAlarmDays().stream()
                .map(day -> AlarmDay.builder()
                        .alarm(savedAlarm)
                        .dayOfWeek(day)
                        .build())
                .collect(Collectors.toList());
        savedAlarm.saveAlarmDays(alarmDays);

        alarmJPARepository.save(savedAlarm);

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


    @Transactional
    public ResponseEntity<?> updateAlarm(Long alarmId, AlarmDTO alarmDTO){
        Optional<Alarm> alarm = alarmJPARepository.findById(alarmId);

        List<AlarmDay> alarmDays = alarmDTO.getAlarmDays().stream()
                .map(day -> AlarmDay.builder()
                        .alarm(alarm.get())
                        .dayOfWeek(day)
                        .build())
                .collect(Collectors.toList());
        alarm.get().updateAlarmDays(alarmDays);


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
    public ResponseEntity<?> getAlarmMessage(Long userId, String alarmTime) {
        TimeLabel timeLabel = TimeLabel.from(alarmTime);

        List<AlarmMessageDTO> alarmMessageDTOList = myBatisAlarmMessageRepository.findRandomUserByUserId(userId, timeLabel);
        long categoryId = alarmMessageDTOList.get(0).getCategoryId();
        List<String> alarmMessageTemplates = myBatisAlarmMessageRepository.findRandomAlarmMessageTemplate(categoryId, timeLabel);

        for (int i=0; i <alarmMessageDTOList.size(); i++){
            AlarmMessageDTO dto = alarmMessageDTOList.get(i);
            dto.setMessageTemplate(alarmMessageTemplates.get(i));
        }



        List<String> alarmMessages = buildAlarmMessage(alarmMessageDTOList);

        // 성능을 위해 알람 인스턴스 저장은 비동기 처리
        // 또한 단일책임원칙을 위해 역할을 분리하기도 위함
        alarmHistoryService.saveAlarmInstanceAsync(userId, alarmMessageDTOList, alarmMessages);

        return ResponseEntity.ok(ApiResponse.success(alarmMessages));
    }

    private List<String> buildAlarmMessage(List<AlarmMessageDTO> dtos){
        List<String> messages = new ArrayList<>();

        for (AlarmMessageDTO dto : dtos){
            String formattedTime = dto.getTime().format(DateTimeFormatter.ofPattern("HH:mm"));
            messages.add(dto.getMessageTemplate()
                    .replace("{username}", dto.getName())
                    .replace("{time}", formattedTime));
        }

        return messages;
    }

    public List<AlarmDTO> getAlarmList(Long userId) {

        UserEntity user = userJPARepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        List<AlarmDTO> dtoList = alarmJPARepository.findAllByUser(user).stream()
                .map(AlarmDTO::fromEntity)
                .toList();

        return dtoList;
    }
}
