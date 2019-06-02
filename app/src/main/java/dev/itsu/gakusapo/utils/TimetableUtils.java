package dev.itsu.gakusapo.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dev.itsu.gakusapo.MainApplication;
import dev.itsu.gakusapo.R;
import dev.itsu.gakusapo.api.service.PreferencesService;
import dev.itsu.gakusapo.entity.Subject;
import dev.itsu.gakusapo.entity.Timetable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TimetableUtils {

    public static List<Subject> jsonToSubjectList(String json) {
        return (List<Subject>) new Gson().fromJson(json, new TypeToken<List<Subject>>(){}.getType());
    }

    public static String listToSubjectJson(List<Subject> subjects) {
        return new Gson().toJson(subjects);
    }

    public static Timetable createNewTimetable(String name) {
        return createNewTimetable(name, Timetable.DAY_TYPE_MONDAY_TO_FRIDAY, Timetable.TIME_TYPE_7);
    }

    public static Timetable createNewTimetable(String name, int dayType, int timeType) {
        Timetable timetable = new Timetable(name, dayType, timeType);
        List<Subject> subjects = new LinkedList<>();
        int day = 0;
        int time = 1;

        for (int i = 0; i < timetable.getDayType() * timetable.getTimeType(); i++) {
            subjects.add(new Subject(String.valueOf(i), "教室", "", timetable.getName(), day, time, R.color.subjectGray, i));

            day++;
            if (day == timetable.getDayType()) {
                day = 0;
                time++;
            }
        }
        timetable.setSubjects(subjects);
        return timetable;
    }

    public static List<Subject> sortByPosition(List<Subject> subjects) {
        Collections.sort(subjects, new Comparator<Subject>() {
            @Override
            public int compare(Subject o1, Subject o2) {
                return o1.getPosition() - o2.getPosition();
            }
        });
        return subjects;
    }

    public static int dayTypeStringToInt(String type) {
        if (type.equals(MainApplication.getContext().getResources().getStringArray(R.array.timetableDayType)[0])) return Timetable.DAY_TYPE_MONDAY_TO_FRIDAY;
        else return Timetable.DAY_TYPE_MONDAY_TO_SATURDAY;
    }

    public static int timeTypeStringToInt(String type) {
        String[] array = MainApplication.getContext().getResources().getStringArray(R.array.timetableTimeType);
        if (type.equals(array[0])) return Timetable.TIME_TYPE_4;
        else if (type.equals(array[1])) return Timetable.TIME_TYPE_5;
        else if (type.equals(array[2])) return Timetable.TIME_TYPE_6;
        else if (type.equals(array[3])) return Timetable.TIME_TYPE_7;
        else return Timetable.TIME_TYPE_8;
    }

    public static List<Subject> getDaySubjects(Timetable timetable, int day) {
        List<Subject> result = new ArrayList<>();
        for (int i = day; i < timetable.getSubjects().size(); i = i + timetable.getDayType()) {
            Subject subject = timetable.getSubjects().get(i);
            if (subject == null) break;
            result.add(timetable.getSubjects().get(i));
        }
        return sortByPosition(result);
    }

    public static int dayToWeek(String dateStr) {
        Date date = fromString(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK) - 2;
    }

    public static String parseDate(Date date) {
        return new SimpleDateFormat("yyyy/MM/dd").format(date);
    }

    public static Date fromString(String str) {
        try {
            return new SimpleDateFormat("yyyy/MM/dd").parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static String positionToDayAndTime(int position, int dayType, int timeType) {
        String data = "%s曜%s限";
        String dayStr = "";
        String timeStr = "";

        int day = position % dayType;

        switch (day) {
            case 0:
                dayStr = "月";
                break;
            case 1:
                dayStr = "火";
                break;
            case 2:
                dayStr = "水";
                break;
            case 3:
                dayStr = "木";
                break;
            case 4:
                dayStr = "金";
                break;
            case 5:
                if (dayType == Timetable.DAY_TYPE_MONDAY_TO_FRIDAY) dayStr = "金";
                else if (dayType == Timetable.DAY_TYPE_MONDAY_TO_SATURDAY) dayStr = "土";
                break;
        }

        int time = position / dayType;
        switch (time) {
            case 0:
                timeStr = "1";
                break;
            case 1:
                timeStr = "2";
                break;
            case 2:
                timeStr = "3";
                break;
            case 3:
                timeStr = "4";
                break;
            case 4:
                timeStr = "5";
                break;
            case 5:
                timeStr = "6";
                break;
            case 6:
                timeStr = "7";
                break;
            case 7:
                timeStr = "8";
                break;
        }

        return String.format(data, dayStr, timeStr);
    }

    public static int colorIdToDBColor(int colorId) {
        switch (colorId) {
            case R.color.subjectRed: return 0;
            case R.color.subjectMagenta: return 1;
            case R.color.subjectLightPurple: return 2;
            case R.color.subjectDarkPurple: return 3;
            case R.color.subjectIndigo: return 4;
            case R.color.subjectLightBlue: return 5;
            case R.color.subjectDarkBlue: return 6;
            case R.color.subjectDiamond: return 7;
            case R.color.subjectForest: return 8;
            case R.color.subjectDarkGreen: return 9;
            case R.color.subjectLightGreen: return 10;
            case R.color.subjectDarkYellow: return 11;
            case R.color.subjectOrange: return 12;
            case R.color.subjectDarkOrange: return 13;
            case R.color.subjectLightOrange: return 14;
            case R.color.subjectBrown: return 15;
            case R.color.subjectGray: return 16;
            default: return 0;
        }
    }

    public static int DBColorToColorId(int DBColorId) {
        switch (DBColorId) {
            case 0: return R.color.subjectRed;
            case 1: return R.color.subjectMagenta;
            case 2: return R.color.subjectLightPurple;
            case 3: return R.color.subjectDarkPurple;
            case 4: return R.color.subjectIndigo;
            case 5: return R.color.subjectLightBlue;
            case 6: return R.color.subjectDarkBlue;
            case 7: return R.color.subjectDiamond;
            case 8: return R.color.subjectForest;
            case 9: return R.color.subjectDarkGreen;
            case 10: return R.color.subjectLightGreen;
            case 11: return R.color.subjectDarkYellow;
            case 12: return R.color.subjectOrange;
            case 13: return R.color.subjectDarkOrange;
            case 14: return R.color.subjectLightOrange;
            case 15: return R.color.subjectBrown;
            case 16: return R.color.subjectGray;
            default: return R.color.subjectGray;
        }
    }

    public static String getDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String date;

        if (hour >= PreferencesService.getScheduleReloadTime()) {
            date = String.format("%s/%s/%s", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE) + 1);
        } else {
            date = String.format("%s/%s/%s", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE));
        }

        try {
            return sdf.format(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isTomorrow() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= PreferencesService.getScheduleReloadTime();
    }
}
