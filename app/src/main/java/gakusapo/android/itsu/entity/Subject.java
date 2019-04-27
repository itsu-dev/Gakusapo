package gakusapo.android.itsu.entity;

import gakusapo.android.itsu.utils.TimetableUtils;

public class Subject {

    public static final int DAY_MONDAY = 0;
    public static final int DAY_TUESDAY = 1;
    public static final int DAY_WEDNESDAY = 2;
    public static final int DAY_THURSDAY = 3;
    public static final int DAY_FRIDAY = 4;
    public static final int DAY_SATURDAY = 5;

    private String name;
    private String className;
    private String timetableName;
    private String description;
    private int day;
    private int time;
    private int background;
    private int position;

    public Subject() {
        this(null, null, null, null, 0, 0, 0, 0);
    }

    public Subject(String name, String className, String description, String timetableName, int day, int time, int background, int position) {
        this.name = name;
        this.className = className;
        this.description = description;
        this.timetableName = timetableName;
        this.day = day;
        this.time = time;
        this.background = background;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTimetableName() {
        return timetableName;
    }

    public void setTimetableName(String timetableName) {
        this.timetableName = timetableName;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getBackground() {
        return TimetableUtils.DBColorToColorId(background);
    }

    public void setBackground(int background) {
        this.background = TimetableUtils.colorIdToDBColor(background);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
