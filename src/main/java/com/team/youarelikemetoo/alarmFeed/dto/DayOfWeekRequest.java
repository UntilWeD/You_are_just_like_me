package com.team.youarelikemetoo.alarmFeed.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DayOfWeekRequest {
    private List<Integer> dayOfWeek;

    @Override
    public String toString() {
        return "DayOfWeekRequest{" +
                "dayOfWeek=" + dayOfWeek +
                '}';
    }
}
