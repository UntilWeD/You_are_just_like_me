package com.team.youarelikemetoo.alarm.entity;

import com.team.youarelikemetoo.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "alarm_instance")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlarmInstance {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_instance_id")
    private Long id;

    @Column(name = "rendered_message")
    private String renderedMessage;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_alarm_id")
    private Alarm sourceAlarm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_user_id")
    private UserEntity targetUser;

    @Builder
    public AlarmInstance(String renderedMessage, LocalDateTime createdAt, Alarm sourceAlarm, UserEntity targetUser) {
        this.renderedMessage = renderedMessage;
        this.createdAt = createdAt;
        this.sourceAlarm = sourceAlarm;
        this.targetUser = targetUser;
    }
}
