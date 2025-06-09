package com.team.youarelikemetoo.alarm.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "alarm_message_template")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlarmMessageTemplate {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_message_template_id")
    private Long id;

    @Column(name = "message_template")
    private String messageTemplate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

}
