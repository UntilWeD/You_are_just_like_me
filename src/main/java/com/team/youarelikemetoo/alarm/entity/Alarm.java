package com.team.youarelikemetoo.alarm.entity;

import com.team.youarelikemetoo.alarm.dto.AlarmDTO;
import com.team.youarelikemetoo.alarm.util.DayOfWeekConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;


@Table(name = "alarm")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String category;

    @Column(name = "time")
    private LocalTime time;

    // EX) 0,1,2,3,4,5,6 (0 = 일요일, 6 = 토요일)
    @Convert(converter = DayOfWeekConverter.class)
    @Column(name = "dayofweek")
    private List<Integer> dayOfWeek;

    @Column(name = "isrepeating")
    private boolean isRepeating;

    @Builder
    public Alarm(Long id, String title,String description, String category, LocalTime time, List<Integer> dayOfWeek, boolean isRepeating) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.time = time;
        this.dayOfWeek = dayOfWeek;
        this.isRepeating = isRepeating;
    }

    public void updateAlarmStatus(AlarmDTO alarmDTO){
        this.title = alarmDTO.getTitle();
        this.description = alarmDTO.getDescription();
        this.category = alarmDTO.getCategory();
        this.time = alarmDTO.getTime();
        this.dayOfWeek = alarmDTO.getDayOfWeek();
        this.isRepeating = alarmDTO.isRepeating();
    }
}
