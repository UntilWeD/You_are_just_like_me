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
    @Column(name = "alarm_id")
    private Long id;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "creator_id")
    private Long creatorId;

    private String title;

    private String description;


    @Column(name = "time")
    private LocalTime time;

    // EX) 0,1,2,3,4,5,6 (0 = 일요일, 6 = 토요일)
    @Convert(converter = DayOfWeekConverter.class)
    @Column(name = "dayofweek")
    private List<Integer> dayOfWeek;

    private String name;

    @Column(name = "isrepeating")
    private boolean isRepeating;

    @Builder
    public Alarm(Long id, Long categoryId, Long creatorId, String title, String description, LocalTime time, List<Integer> dayOfWeek, String name, boolean isRepeating) {
        this.id = id;
        this.categoryId = categoryId;
        this.creatorId = creatorId;
        this.title = title;
        this.description = description;
        this.time = time;
        this.dayOfWeek = dayOfWeek;
        this.name = name;
        this.isRepeating = isRepeating;
    }

    public void updateAlarmStatus(AlarmDTO alarmDTO){
        this.title = alarmDTO.getTitle();
        this.description = alarmDTO.getDescription();
        this.categoryId = alarmDTO.getCategoryId();
        this.time = alarmDTO.getTime();
        this.dayOfWeek = alarmDTO.getDayOfWeek();
        this.isRepeating = alarmDTO.isRepeating();
    }
}
