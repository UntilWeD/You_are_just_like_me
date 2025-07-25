package com.team.youarelikemetoo.alarmFeed.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "alarm_feed_image")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AlarmFeedImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_feed_image_id")
    private int id;

    @Column(name = "original_filename")
    private String originalFilename;

    @Column(name = "image_path")
    private String imagePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarm_feed_id")
    private AlarmFeed alarmFeed;

    @Builder
    public AlarmFeedImage(String originalFilename, String imagePath, AlarmFeed alarmFeed) {
        this.originalFilename = originalFilename;
        this.imagePath = imagePath;
        this.alarmFeed = alarmFeed;
    }
}
