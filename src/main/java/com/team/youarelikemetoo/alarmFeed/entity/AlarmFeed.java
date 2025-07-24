package com.team.youarelikemetoo.alarmFeed.entity;

import com.team.youarelikemetoo.alarm.entity.Alarm;
import com.team.youarelikemetoo.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "alarm_feed")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AlarmFeed {

    @Id
    @GeneratedValue
    @Column(name = "alarm_feed_id")
    private Long id;

    @Column(name = "feed_content")
    private String feedContent;

    @Column(name = "like_count")
    private int likeCount;

    @Column(name = "share_count")
    private int shareCount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Builder
    public AlarmFeed(String feedContent, int likeCount, int shareCount, UserEntity user) {
        this.feedContent = feedContent;
        this.likeCount = likeCount;
        this.shareCount = shareCount;
        this.user = user;
    }
}
