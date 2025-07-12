package com.team.youarelikemetoo.alarm.entity;

import org.springframework.cglib.core.Local;

import java.time.LocalTime;

public enum TimeLabel {
    MORNING(LocalTime.of(6, 0), LocalTime.of(11, 59)),
    LUNCH(LocalTime.of(12, 0), LocalTime.of(13, 59)),
    AFTERNOON(LocalTime.of(14, 0), LocalTime.of(17, 59)),
    EVENING(LocalTime.of(18, 0), LocalTime.of(21, 59)),
    NIGHT(LocalTime.of(22, 0), LocalTime.of(23, 59)),
    DAWN(LocalTime.of(0, 0), LocalTime.of(5, 59));
    private final LocalTime start;
    private final LocalTime end;



    TimeLabel(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    public boolean isWithin(LocalTime time){
        return !time.isBefore(start) && !time.isAfter(end);
    }

    public static TimeLabel from(String alarmTime){

        String[] temp = alarmTime.split("-");
        LocalTime time = LocalTime.of(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));

        for(TimeLabel label : values()){
            if(label.isWithin(time)){
                return label;
            }
        }
        throw new IllegalArgumentException("Invalid Time Period.");
    }

    @Override
    public String toString() {
        return "TimeLabel{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
