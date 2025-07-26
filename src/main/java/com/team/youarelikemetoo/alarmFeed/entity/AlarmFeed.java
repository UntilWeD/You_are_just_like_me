package com.team.youarelikemetoo.alarmFeed.entity;

import com.team.youarelikemetoo.alarm.util.DayOfWeekConverter;
import com.team.youarelikemetoo.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

    @Convert(converter = DayOfWeekConverter.class)
    @Column(name = "dayofweek")
    private List<Integer> dayOfWeek;

    @Column(name = "isrepeating")
    private boolean isRepeating;

    @Column(name = "like_count")
    private int likeCount;

    @Column(name = "share_count")
    private int shareCount;

    @OneToMany(mappedBy = "alarmFeed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlarmFeedImage> images = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Builder
    public AlarmFeed(String feedContent, String title, String description, LocalTime time, List<Integer> dayOfWeek, boolean isRepeating, int likeCount, int shareCount, UserEntity user) {
        this.feedContent = feedContent;
        this.title = title;
        this.description = description;
        this.time = time;
        this.dayOfWeek = dayOfWeek;
        this.isRepeating = isRepeating;
        this.likeCount = likeCount;
        this.shareCount = shareCount;
        this.user = user;
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
}
