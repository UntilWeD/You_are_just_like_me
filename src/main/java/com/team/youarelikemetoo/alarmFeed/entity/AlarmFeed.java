package com.team.youarelikemetoo.alarmFeed.entity;

import com.team.youarelikemetoo.alarm.util.DayOfWeekConverter;
import com.team.youarelikemetoo.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Table(name = "alarm_feed")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AlarmFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_feed_id")
    private Long id;

    @Column(name = "feed_content")
    private String feedContent;

    @Column(name= "title")
    private String title;

    @Column(name= "description")
    private String description;

    @Column(name = "time")
    private LocalTime time;

    @OneToMany(mappedBy = "alarmFeed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlarmFeedDay> alarmFeedDays;

    @Column(name = "repeat_count")
    private int repeatCount;


    @Column(name = "time_interval")
    private int timeInterval;


    @Column(name = "like_count")
    private int likeCount;

    @Column(name = "share_count")
    private int shareCount;

    @OneToMany(mappedBy = "alarmFeed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlarmFeedImage> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Builder
    public AlarmFeed(Long id, String feedContent, String title, String description, LocalTime time, List<AlarmFeedDay> alarmFeedDays, int repeatCount, int timeInterval, int likeCount, int shareCount, List<AlarmFeedImage> images, UserEntity user) {
        this.feedContent = feedContent;
        this.title = title;
        this.description = description;
        this.time = time;
        this.alarmFeedDays = alarmFeedDays;
        this.repeatCount = repeatCount;
        this.timeInterval = timeInterval;
        this.likeCount = likeCount;
        this.shareCount = shareCount;
        this.images = images != null ? images : new ArrayList<>();
        this.user = user;
    }

    public void saveAlarmFeedDays(List<AlarmFeedDay> alarmFeedDays){
        this.alarmFeedDays = alarmFeedDays;
    }

    public void updateAlarmFeedDays(List<AlarmFeedDay> alarmFeedDays){
        this.alarmFeedDays.clear();
        this.alarmFeedDays.addAll(alarmFeedDays);
    }



    public void addLikeCount(){
        this.likeCount++;
    }

    public void minusLikeCount(){
        this.likeCount--;
    }

    public void addShareCount() {
        this.shareCount++;
    }

    public void minusShareCount(){
        this.shareCount--;
    }

    public List<Integer> extractDayOfWeeks() {
        return alarmFeedDays.stream()
                .map(AlarmFeedDay::getDayOfWeek)
                .collect(Collectors.toList());
    }
}
