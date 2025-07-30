package com.team.youarelikemetoo.alarm.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "alarm_day")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlarmDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_day_id")
    private Long id;

    @Column(name = "day_of_week")
    private Integer dayOfWeek;

    @ManyToOne
    @JoinColumn(name = "alarm_id")
    private Alarm alarm;

    @Builder
    public AlarmDay(Integer dayOfWeek, Alarm alarm) {
        this.dayOfWeek = dayOfWeek;
        this.alarm = alarm;
    }
}
