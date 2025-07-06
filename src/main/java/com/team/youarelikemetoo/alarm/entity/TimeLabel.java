package com.team.youarelikemetoo.alarm.entity;

import org.springframework.cglib.core.Local;

import java.time.LocalTime;

public enum TimeLabel {
    MORNING(LocalTime.of(6,0), LocalTime.of(11, 59));

    private final LocalTime start;
    private final LocalTime end;



    TimeLabel(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    public boolean isWithin(LocalTime time){
        return !time.isBefore(start) && !time.isAfter(end);
    }

    public static TimeLabel from(LocalTime time){
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
