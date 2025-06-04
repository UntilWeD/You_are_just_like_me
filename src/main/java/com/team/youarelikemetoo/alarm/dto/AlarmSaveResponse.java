package com.team.youarelikemetoo.alarm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class AlarmSaveResponse {
    private Long alarmId;
    private LocalDateTime createdAt;
}
