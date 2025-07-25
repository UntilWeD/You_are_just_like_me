package com.team.youarelikemetoo.alarmFeed.entity;

import com.team.youarelikemetoo.alarmFeed.dto.AlarmFeedCommentDTO;
import com.team.youarelikemetoo.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "alarm_feed_comment")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AlarmFeedComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_feed_comment_id")
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "alarm_feed_id")
    private AlarmFeed alarmFeed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Builder
    public AlarmFeedComment(String content, AlarmFeed alarmFeed, UserEntity user) {
        this.content = content;
        this.alarmFeed = alarmFeed;
        this.user = user;
    }

    public void updateAlarmFeedComment(AlarmFeedCommentDTO dto){
        this.content = dto.getContent();
    }
}
