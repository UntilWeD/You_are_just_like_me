package com.team.youarelikemetoo.alarm.dto;

import com.team.youarelikemetoo.alarm.entity.Alarm;
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

    private List<Integer> dayOfWeek;

    private String nickname;

    private boolean isRepeating;

    public Alarm toEntity(){
        Alarm alarm = Alarm.builder()
                .title(this.title)
                .description(this.description)
                .category(this.category)
                .time(this.time)
                .dayOfWeek(this.dayOfWeek)
                .nickname(this.nickname)
                .isRepeating(this.isRepeating)
                .build();
        return alarm;
    }

    public static AlarmDTO fromEntity(Alarm alarm){
        AlarmDTO alarmDTO = AlarmDTO.builder()
                .title(alarm.getTitle())
                .description(alarm.getDescription())
                .category(alarm.getCategory())
                .time(alarm.getTime())
                .dayOfWeek(alarm.getDayOfWeek())
                .nickname(alarm.getNickname())
                .isRepeating(alarm.isRepeating())
                .build();
        return alarmDTO;
    }



}
