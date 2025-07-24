package com.team.youarelikemetoo.alarmFeed.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "alarm_feed_image")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AlarmFeedImage {

    @Id
    @GeneratedValue
    @Column(name = "alarm_feed_image_id")
    private int id;

    @Column(name = "original_filename")
    private String originalFilename;

    @Column(name = "unique_filename")
    private String uniqueFilename;

    @Column(name = "image_path")
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "alarm_feed_id")
    private AlarmFeed alarmFeed;

    @Builder
    public AlarmFeedImage(String originalFilename, String uniqueFilename, String imagePath, AlarmFeed alarmFeed) {
        this.originalFilename = originalFilename;
        this.uniqueFilename = uniqueFilename;
        this.imagePath = imagePath;
        this.alarmFeed = alarmFeed;
    }
}
