package com.team.youarelikemetoo.alarm.dto;

import com.team.youarelikemetoo.alarm.entity.Alarm;
import com.team.youarelikemetoo.alarm.entity.Category;
import com.team.youarelikemetoo.alarm.entity.TimeLabel;
import com.team.youarelikemetoo.user.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.List;

@Builder
@Setter
@Getter
@ToString
public class AlarmDTO {

    private String title;

    private String description;

    private String category;

    private LocalTime time;
    private TimeLabel timeLabel;

    private List<Integer> dayOfWeek;

    private boolean isRepeating;

    public Alarm toEntity(UserEntity user, Category category){
        Alarm alarm = Alarm.builder()
                .title(this.title)
                .description(this.description)
                .category(category)
                .user(user)
                .time(this.time)
                .timeLabel(TimeLabel.from(time))
                .dayOfWeek(this.dayOfWeek)
                .isRepeating(this.isRepeating)
                .build();
        return alarm;
    }

    public static AlarmDTO fromEntity(Alarm alarm){
        AlarmDTO alarmDTO = AlarmDTO.builder()
                .title(alarm.getTitle())
                .description(alarm.getDescription())
                .time(alarm.getTime())
                .timeLabel(alarm.getTimeLabel())
                .dayOfWeek(alarm.getDayOfWeek())
                .isRepeating(alarm.isRepeating())
                .build();
        return alarmDTO;
    }



}
