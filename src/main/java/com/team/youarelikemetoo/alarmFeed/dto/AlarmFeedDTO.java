package com.team.youarelikemetoo.alarmFeed.dto;

import com.team.youarelikemetoo.alarm.dto.AlarmDTO;
import com.team.youarelikemetoo.alarm.entity.Alarm;
import com.team.youarelikemetoo.alarmFeed.entity.AlarmFeed;
import com.team.youarelikemetoo.alarmFeed.entity.AlarmFeedImage;
import com.team.youarelikemetoo.user.entity.UserEntity;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class AlarmFeedDTO {

    private Long id;
    private String feedContent;
    private List<String> imageUrls;

    @Column(name= "title")
    private String title;

    private String description;

    private LocalTime time;

    private List<Integer> alarmFeedDays;

    private boolean isRepeating;
    private int likeCount;
    private int shareCount;

    public AlarmFeed toEntity(UserEntity user){
        AlarmFeed alarmFeed = AlarmFeed.builder()
                .feedContent(this.feedContent)
                .title(this.title)
                .description(this.description)
                .time(this.time)
                .isRepeating(this.isRepeating)
                .likeCount(this.likeCount)
                .shareCount(this.shareCount)
                .user(user)
                .build();
        return alarmFeed;
    }

    public static AlarmFeedDTO fromEntity(AlarmFeed entity){
        AlarmFeedDTO dto = new AlarmFeedDTO();
        dto.setId(entity.getId());
        dto.setFeedContent(entity.getFeedContent());
        dto.setImageUrls(
                entity.getImages().stream()
                        .map(AlarmFeedImage::getImageUrl)
                        .toList()
        );
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setTime(entity.getTime());
        dto.setAlarmFeedDays(entity.getAlarmFeedDays().stream().map(
                day -> day.getDayOfWeek()).collect(Collectors.toList())
        );
        dto.setRepeating(dto.isRepeating);
        dto.setLikeCount(dto.getLikeCount());
        dto.setShareCount(dto.getShareCount());
        return dto;
    }

}
