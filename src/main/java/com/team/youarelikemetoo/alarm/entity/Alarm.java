package com.team.youarelikemetoo.alarm.entity;

import com.team.youarelikemetoo.alarm.dto.AlarmDTO;
import com.team.youarelikemetoo.alarm.util.DayOfWeekConverter;
import com.team.youarelikemetoo.user.entity.UserEntity;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private UserEntity user;

    private String title;

    private String description;


    @Column(name = "time")
    private LocalTime time;

    @Column(name = "time_label")
    private TimeLabel timeLabel;

    // EX) 0,1,2,3,4,5,6 (0 = 일요일, 6 = 토요일)
    @Convert(converter = DayOfWeekConverter.class)
    @Column(name = "dayofweek")
    private List<Integer> dayOfWeek;

    @Column(name = "isrepeating")
    private boolean isRepeating;

    @Builder
    public Alarm(Category category, UserEntity user, String title, String description, LocalTime time, TimeLabel timeLabel,
                 List<Integer> dayOfWeek, boolean isRepeating) {
        this.category = category;
        this.user = user;
        this.title = title;
        this.description = description;
        this.time = time;
        this.timeLabel = timeLabel;
        this.dayOfWeek = dayOfWeek;
        this.isRepeating = isRepeating;
    }


    public void updateAlarmStatus(AlarmDTO alarmDTO, Category category){
        this.title = alarmDTO.getTitle();
        this.description = alarmDTO.getDescription();
        this.category = category;
        this.time = alarmDTO.getTime();
        this.timeLabel = TimeLabel.from(time);

        this.dayOfWeek = alarmDTO.getDayOfWeek();
        this.isRepeating = alarmDTO.isRepeating();
    }
}
