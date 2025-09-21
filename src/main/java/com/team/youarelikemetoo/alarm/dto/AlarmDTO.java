package com.team.youarelikemetoo.alarm.dto;

import com.team.youarelikemetoo.alarm.entity.Alarm;
import com.team.youarelikemetoo.alarm.entity.AlarmDay;
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
import java.util.stream.Collectors;

@Builder
@Setter
@Getter
@ToString
public class AlarmDTO {
    private Long id;

    private String title;

    private String description;

    private String category;

    private LocalTime time;
    private TimeLabel timeLabel;

    private List<Integer> alarmDays;

    private int repeatCount;
    private int timeInterval;

    public Alarm toEntity(UserEntity user, Category category){
        Alarm alarm = Alarm.builder()
                .title(this.title)
                .description(this.description)
                .category(category)
                .user(user)
                .time(this.time)
                .timeLabel(TimeLabel.from(time))
                .repeatCount(this.repeatCount)
                .timeInterval(this.timeInterval)
                .build();
        return alarm;
    }

    public static AlarmDTO fromEntity(Alarm entity){
        AlarmDTO alarmDTO = AlarmDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .category(entity.getCategory().getCategoryName())
                .time(entity.getTime())
                .timeLabel(entity.getTimeLabel())
                .alarmDays(entity.getAlarmDays().stream().map(
                        day -> day.getDayOfWeek()).collect(Collectors.toList()))
                .repeatCount(entity.getRepeatCount())
                .timeInterval(entity.getTimeInterval())
                .build();
        return alarmDTO;
    }



}
