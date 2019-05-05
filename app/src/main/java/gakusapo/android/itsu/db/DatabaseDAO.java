package gakusapo.android.itsu.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gakusapo.android.itsu.entity.DateEvent;
import gakusapo.android.itsu.entity.Timetable;
import gakusapo.android.itsu.entity.Train;
import gakusapo.android.itsu.utils.TimetableUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DatabaseDAO {

    private static SQLiteDatabase writableDatabase;

    static {
        writableDatabase = DatabaseHelper.DatabaseHelperFactory.getInstance().getWritableDatabase();
    }

    public static Map<String, Timetable> getTimetables() {
        Map<String, Timetable> result = new HashMap<>();

        Cursor cursor = writableDatabase.query(
                DatabaseHelper.TABLE_TIMETABLE,
                new String[]{"name", "data", "dayType", "timeType"},
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            result.put(cursor.getString(0), new Timetable(
                    cursor.getString(0),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    TimetableUtils.jsonToSubjectList(cursor.getString(1))
            ));
            cursor.moveToNext();
        }

        return result;
    }

    public static boolean addTimetable(Timetable timetable) {
        if (getTimetables().containsKey(timetable.getName())) return false;

        ContentValues values = new ContentValues();
        values.put("name", timetable.getName());
        values.put("data", TimetableUtils.listToSubjectJson(timetable.getSubjects()));
        values.put("dayType", timetable.getDayType());
        values.put("timeType", timetable.getTimeType());

        writableDatabase.insert(DatabaseHelper.TABLE_TIMETABLE, null, values);

        return true;
    }

    public static boolean updateTimetable(Timetable timetable) {
        if (!getTimetables().containsKey(timetable.getName())) return false;

        ContentValues values = new ContentValues();
        values.put("name", timetable.getName());
        values.put("data", TimetableUtils.listToSubjectJson(timetable.getSubjects()));
        values.put("dayType", timetable.getDayType());
        values.put("timeType", timetable.getTimeType());

        writableDatabase.update(DatabaseHelper.TABLE_TIMETABLE, values,  "name = '" + timetable.getName() + "'", null);

        return true;
    }

    public static DateEvent getDateEvent(String date) {
        DateEvent event = null;
        Gson gson = new Gson();

        Cursor cursor = writableDatabase.query(
                DatabaseHelper.TABLE_DATEEVENT,
                new String[]{"date", "memo", "homeworks", "submissions", "tests", "classes", "events", "reminders"},
                "date = ?",
                new String[]{date},
                null,
                null,
                null
        );

        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            event = new DateEvent(cursor.getString(0));
            event.setMemo(cursor.getString(1));
            event.setHomeworks((List<String>) gson.fromJson(cursor.getString(2), new TypeToken<List>(){}.getType()));
            event.setSubmissions((List<String>) gson.fromJson(cursor.getString(3), new TypeToken<List>(){}.getType()));
            event.setTests((List<String>) gson.fromJson(cursor.getString(4), new TypeToken<List>(){}.getType()));
            event.setClasses((List<String>) gson.fromJson(cursor.getString(5), new TypeToken<List>(){}.getType()));
            event.setEvents((List<String>) gson.fromJson(cursor.getString(6), new TypeToken<List>(){}.getType()));
            event.setReminders((Map<String, Boolean>) gson.fromJson(cursor.getString(7), new TypeToken<Map<String, Boolean>>(){}.getType()));
        }

        return event;
    }

    public static boolean addDateEvent(DateEvent event) {
        if (getDateEvent(event.getDate()) != null) return false;

        Gson gson = new Gson();

        ContentValues values = new ContentValues();
        values.put("date", event.getDate());
        values.put("memo", event.getMemo());
        values.put("homeworks", gson.toJson(event.getHomeworks()));
        values.put("submissions", gson.toJson(event.getSubmissions()));
        values.put("tests", gson.toJson(event.getTests()));
        values.put("classes", gson.toJson(event.getClasses()));
        values.put("events", gson.toJson(event.getEvents()));
        values.put("reminders", gson.toJson(event.getReminders()));

        writableDatabase.insert(DatabaseHelper.TABLE_DATEEVENT, null, values);

        return true;
    }

    public static boolean updateDateEvent(DateEvent event) {
        if (getDateEvent(event.getDate()) == null) return false;

        Gson gson = new Gson();

        ContentValues values = new ContentValues();
        values.put("date", event.getDate());
        values.put("memo", event.getMemo());
        values.put("homeworks", gson.toJson(event.getHomeworks()));
        values.put("submissions", gson.toJson(event.getSubmissions()));
        values.put("tests", gson.toJson(event.getTests()));
        values.put("classes", gson.toJson(event.getClasses()));
        values.put("events", gson.toJson(event.getEvents()));
        values.put("reminders", gson.toJson(event.getReminders()));

        writableDatabase.update(DatabaseHelper.TABLE_DATEEVENT, values,  "date = '" + event.getDate() + "'", null);

        return true;
    }

    public static void addTrain(String company, String name) {
        if (getTrains().containsKey(name)) return;

        ContentValues values = new ContentValues();
        values.put("company", company);
        values.put("name", name);

        writableDatabase.insert(DatabaseHelper.TABLE_TRAIN, null, values);
    }

    public static Map<String, Train> getTrains() {
        Map<String, Train> result = new LinkedHashMap<>();

        Cursor cursor = writableDatabase.query(
                DatabaseHelper.TABLE_TRAIN,
                new String[]{"company", "name"},
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            result.put(cursor.getString(1), new Train(cursor.getString(0), cursor.getString(1)));
            cursor.moveToNext();
        }

        return result;
    }

    public static void closeDatabase() {
        writableDatabase.close();
    }
}
