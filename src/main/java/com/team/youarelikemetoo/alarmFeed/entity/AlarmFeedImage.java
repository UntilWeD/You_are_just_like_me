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

    @Column(name = "unique_filename")
    private String uniqueFilename;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "blob_name")
    private String blobName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarm_feed_id")
    private AlarmFeed alarmFeed;


    @Builder
    public AlarmFeedImage(String uniqueFilename, String imageUrl, String blobName, AlarmFeed alarmFeed) {
        this.uniqueFilename = uniqueFilename;
        this.imageUrl = imageUrl;
        this.blobName = blobName;
        this.alarmFeed = alarmFeed;
    }
}
