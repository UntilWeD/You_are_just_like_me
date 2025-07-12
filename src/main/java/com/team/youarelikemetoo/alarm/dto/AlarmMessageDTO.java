package com.team.youarelikemetoo.alarm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlarmMessageDTO {

    private String name;
    private LocalTime time;
    private String messageTemplate;
    private long categoryId;

    // 알람 인스턴스 저장시 필요한 속성
    private Long sourceAlarmId;
}
