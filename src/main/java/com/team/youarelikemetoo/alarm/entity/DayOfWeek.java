package com.team.youarelikemetoo.alarm.entity;

public enum DayOfWeek {
    SUNDAY(0), MONDAY(1), TUESDAY(2), WEDNESDAY(3),THURSDAY(4), FRIDAY(5), SATURDAY(6);

    private final int value;

    // 생성자
    DayOfWeek(int value) {
        this.value = value;
    }

    // getter
    public int getValue() {
        return value;
    }

    // 역변환 (int -> enum)
    public static DayOfWeek fromValue(int value) {
        for (DayOfWeek day : DayOfWeek.values()) {
            if (day.getValue() == value) {
                return day;
            }
        }
        throw new IllegalArgumentException("Invalid day value: " + value);
    }
}
