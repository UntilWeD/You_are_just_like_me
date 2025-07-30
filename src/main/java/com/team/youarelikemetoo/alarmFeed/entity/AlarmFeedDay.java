package com.team.youarelikemetoo.alarmFeed.entity;

import com.team.youarelikemetoo.alarm.entity.Alarm;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Table(name = "alarm_feed_day")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlarmFeedDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_feed_day_id")
    private Long id;

    @Column(name = "day_of_week")
    private Integer dayOfWeek;

    @ManyToOne
    @JoinColumn(name = "alarm_feed_id")
    private AlarmFeed alarmFeed;

    @Builder
    public AlarmFeedDay(Integer dayOfWeek, AlarmFeed alarmFeed) {
        this.dayOfWeek = dayOfWeek;
        this.alarmFeed = alarmFeed;
    }

}
