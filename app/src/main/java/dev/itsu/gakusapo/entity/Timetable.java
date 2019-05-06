package dev.itsu.gakusapo.entity;

import java.util.List;

public class Timetable {

    public static final int DAY_TYPE_MONDAY_TO_FRIDAY = 5;
    public static final int DAY_TYPE_MONDAY_TO_SATURDAY =6;

    public static final int TIME_TYPE_5 = 5;
    public static final int TIME_TYPE_6 = 6;
    public static final int TIME_TYPE_7 = 7;
    public static final int TIME_TYPE_8 = 8;

    public static final String DEFAULT_TIMETABLE_NAME = "ba2b084d-2592-4c06-91c8-3bf10c68caa7";

    private String name;
    private int dayType;
    private int timeType;
    private List<Subject> subjects;

    public Timetable(String name, int dayType, int timeType) {
        this(name, dayType, timeType, null);
    }

    public Timetable(String name, int dayType, int timeType, List<Subject> subjects) {
        this.name = name;
        this.dayType = dayType;
        this.timeType = timeType;
        this.subjects = subjects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDayType() {
        return dayType;
    }

    public void setDayType(int dayType) {
        this.dayType = dayType;
    }

    public int getTimeType() {
        return timeType;
    }

    public void setTimeType(int timeType) {
        this.timeType = timeType;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
