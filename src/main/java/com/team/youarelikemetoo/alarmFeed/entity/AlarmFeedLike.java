package com.team.youarelikemetoo.alarmFeed.entity;

import com.team.youarelikemetoo.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "alarm_feed_like")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AlarmFeedLike {

    @Id
    @GeneratedValue
    @Column(name = "alarm_feed_like_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "alarm_feed_id")
    private AlarmFeed alarmFeed;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Builder
    public AlarmFeedLike(AlarmFeed alarmFeed, UserEntity user) {
        this.alarmFeed = alarmFeed;
        this.user = user;
    }
}
